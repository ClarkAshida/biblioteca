package com.imd.repository;

import com.imd.infraestrutura.banco.FabricaConexoes;
import com.imd.model.acervo.*;
import java.sql.*;
import java.util.*;

/** Persistência do catálogo; não realiza operações de empréstimo ou reserva. */
public final class AcervoRepository {
    private final FabricaConexoes fabrica;

    public AcervoRepository(FabricaConexoes fabrica) { this.fabrica = fabrica; }

    public List<Material> listar() throws SQLException {
        try (var c = fabrica.abrirConexao(); var s = c.prepareStatement("SELECT * FROM materiais ORDER BY id");
             var r = s.executeQuery()) {
            List<Material> materiais = new ArrayList<>();
            while (r.next()) {
                Material material;
                if (r.getString("tipo").equals("FISICO")) {
                    var livro = new LivroFisico(r.getLong("id"), r.getString("titulo"), r.getString("autor"), r.getString("isbn"));
                    try (var e = c.prepareStatement("SELECT * FROM exemplares WHERE livro_id = ? ORDER BY codigo")) {
                        e.setLong(1, livro.getId());
                        try (var exemplares = e.executeQuery()) {
                            while (exemplares.next()) livro.adicionarExemplar(new Exemplar(exemplares.getString("codigo"), livro, exemplares.getBoolean("emprestado")));
                        }
                    }
                    material = livro;
                } else if (r.getString("tipo").equals("EBOOK")) {
                    material = new EBook(r.getLong("id"), r.getString("titulo"), r.getString("autor"), r.getString("isbn"), r.getInt("licencas_totais"), r.getInt("licencas_em_uso"));
                } else throw new SQLException("Tipo de material desconhecido: " + r.getString("tipo"));
                materiais.add(material);
            }
            return materiais;
        }
    }

    public long cadastrar(String titulo, String autor, String isbn, Integer licencas, List<String> codigos) throws SQLException {
        try (var c = fabrica.abrirConexao()) {
            c.setAutoCommit(false);
            try {
                long id;
                try (var s = c.prepareStatement("INSERT INTO materiais(tipo,titulo,autor,isbn,licencas_totais,licencas_em_uso) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                    s.setString(1, licencas == null ? "FISICO" : "EBOOK");
                    s.setString(2, titulo); s.setString(3, autor); s.setString(4, isbn);
                    s.setObject(5, licencas); s.setObject(6, licencas == null ? null : 0);
                    s.executeUpdate();
                    try (var ids = s.getGeneratedKeys()) { ids.next(); id = ids.getLong(1); }
                }
                for (String codigo : codigos) inserirExemplar(c, id, codigo);
                c.commit();
                return id;
            } catch (SQLException | RuntimeException e) { c.rollback(); throw e; }
        }
    }

    private void inserirExemplar(Connection c, long id, String codigo) throws SQLException {
        try (var s = c.prepareStatement("INSERT INTO exemplares(codigo,livro_id) VALUES (?,?)")) {
            s.setString(1, codigo); s.setLong(2, id); s.executeUpdate();
        }
    }

    public void adicionarExemplar(long id, String codigo) throws SQLException {
        try (var c = fabrica.abrirConexao(); var s = c.prepareStatement("INSERT INTO exemplares(codigo,livro_id) SELECT ?,id FROM materiais WHERE id=? AND tipo='FISICO'")) {
            s.setString(1, codigo); s.setLong(2, id);
            if (s.executeUpdate() == 0) throw new IllegalArgumentException("Livro físico não encontrado.");
        }
    }

    public void editar(long id, String titulo, String autor, String isbn, Integer licencas) throws SQLException {
        String sql = licencas == null
                ? "UPDATE materiais SET titulo=?,autor=?,isbn=? WHERE id=? AND tipo='FISICO'"
                : "UPDATE materiais SET titulo=?,autor=?,isbn=?,licencas_totais=? WHERE id=? AND tipo='EBOOK' AND licencas_em_uso<=?";
        try (var c = fabrica.abrirConexao(); var s = c.prepareStatement(sql)) {
            s.setString(1, titulo); s.setString(2, autor); s.setString(3, isbn);
            if (licencas == null) s.setLong(4, id);
            else { s.setInt(4, licencas); s.setLong(5, id); s.setInt(6, licencas); }
            if (s.executeUpdate() == 0) throw new IllegalArgumentException("Material não encontrado, tipo incorreto ou licenças abaixo das que estão em uso.");
        }
    }

    public void excluirExemplar(String codigo) throws SQLException {
        try (var c = fabrica.abrirConexao(); var s = c.prepareStatement("DELETE FROM exemplares WHERE codigo=? AND emprestado=FALSE AND (SELECT COUNT(*) FROM exemplares e WHERE e.livro_id=exemplares.livro_id)>1")) {
            s.setString(1, codigo);
            if (s.executeUpdate() == 0) throw new IllegalArgumentException("Exemplar inexistente, emprestado ou último exemplar do livro.");
        }
    }

    public void excluir(long id) throws SQLException {
        try (var c = fabrica.abrirConexao()) {
            c.setAutoCommit(false);
            try {
                try (var s = c.prepareStatement("SELECT id FROM materiais WHERE id=? AND COALESCE(licencas_em_uso,0)=0 AND NOT EXISTS (SELECT 1 FROM exemplares WHERE livro_id=? AND emprestado=TRUE) FOR UPDATE")) {
                    s.setLong(1,id); s.setLong(2,id);
                    try (var r = s.executeQuery()) { if (!r.next()) throw new IllegalArgumentException("Material inexistente ou em uso."); }
                }
                try (var s = c.prepareStatement("DELETE FROM exemplares WHERE livro_id=?")) { s.setLong(1,id); s.executeUpdate(); }
                try (var s = c.prepareStatement("DELETE FROM materiais WHERE id=?")) { s.setLong(1,id); s.executeUpdate(); }
                c.commit();
            } catch (SQLException | RuntimeException e) { c.rollback(); throw e; }
        }
    }
}
