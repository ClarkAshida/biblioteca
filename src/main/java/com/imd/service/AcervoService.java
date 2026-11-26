package com.imd.service;

import com.imd.model.acervo.Material;
import com.imd.repository.AcervoRepository;
import java.sql.SQLException;
import java.util.List;

public final class AcervoService {
    private final AcervoRepository repository;
    public AcervoService(AcervoRepository repository) { this.repository = repository; }
    public List<Material> listar() throws SQLException { return repository.listar(); }

    private String texto(String valor, int limite) {
        if (valor == null || valor.isBlank() || valor.strip().length() > limite)
            throw new IllegalArgumentException("Campo obrigatório; tamanho máximo: " + limite + ".");
        return valor.strip();
    }
    private void licencas(int total) {
        if (total < 1) throw new IllegalArgumentException("Informe pelo menos uma licença.");
    }
    public long cadastrarLivro(String titulo, String autor, String isbn, List<String> codigos) throws SQLException {
        if (codigos == null || codigos.isEmpty()) throw new IllegalArgumentException("Informe pelo menos um exemplar.");
        var normalizados = codigos.stream().map(c -> texto(c, 50)).toList();
        if (normalizados.stream().distinct().count() != normalizados.size()) throw new IllegalArgumentException("Códigos de exemplares duplicados.");
        return repository.cadastrar(texto(titulo,255), texto(autor,200), texto(isbn,30), null, normalizados);
    }
    public long cadastrarEbook(String titulo, String autor, String isbn, int total) throws SQLException {
        licencas(total);
        return repository.cadastrar(texto(titulo,255), texto(autor,200), texto(isbn,30), total, List.of());
    }
    public void editar(long id, String titulo, String autor, String isbn, Integer total) throws SQLException {
        if (total != null) licencas(total);
        repository.editar(id, texto(titulo,255), texto(autor,200), texto(isbn,30), total);
    }
    public void adicionarExemplar(long id, String codigo) throws SQLException { repository.adicionarExemplar(id, texto(codigo,50)); }
    public void excluirExemplar(String codigo) throws SQLException { repository.excluirExemplar(texto(codigo,50)); }
    public void excluir(long id) throws SQLException { repository.excluir(id); }
}
