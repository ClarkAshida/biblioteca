package com.imd.infraestrutura.banco;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseInicialTest {
    private FabricaConexoes fabrica;
    private InicializadorBanco banco;

    @BeforeEach
    void preparar() throws SQLException {
        fabrica = new FabricaConexoes("jdbc:h2:mem:base_" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1", "sa", "");
        banco = new InicializadorBanco(fabrica);
        banco.inicializar();
    }

    @Test
    void inicializarNovamentePreservaOsDados() throws SQLException {
        inserirMaterial("Java");
        banco.inicializar();

        try (var conexao = fabrica.abrirConexao();
             var comando = conexao.createStatement();
             var resultado = comando.executeQuery("SELECT COUNT(*) FROM materiais")) {
            assertTrue(resultado.next());
            assertEquals(1, resultado.getInt(1));
        }
    }

    @Test
    void geraIdAutomaticoEPermiteConsultaEmOutraConexao() throws SQLException {
        long id = inserirMaterial("Introdução a Java");
        assertTrue(id > 0);

        try (var conexao = fabrica.abrirConexao();
             var comando = conexao.prepareStatement("SELECT titulo FROM materiais WHERE id = ?")) {
            comando.setLong(1, id);
            try (var resultado = comando.executeQuery()) {
                assertTrue(resultado.next());
                assertEquals("Introdução a Java", resultado.getString("titulo"));
            }
        }
    }

    @Test
    void exigeCamposObrigatoriosERelacionamentosValidos() {
        assertThrows(SQLException.class, () -> inserirMaterial(null));
        assertThrows(SQLException.class, () -> {
            try (var conexao = fabrica.abrirConexao();
                 var comando = conexao.prepareStatement("INSERT INTO exemplares(codigo, livro_id) VALUES (?, ?)")) {
                comando.setString(1, "EX-001");
                comando.setLong(2, 999L);
                comando.executeUpdate();
            }
        });
    }

    private long inserirMaterial(String titulo) throws SQLException {
        String sql = "INSERT INTO materiais(tipo, titulo, autor, isbn) VALUES (?, ?, ?, ?)";
        try (var conexao = fabrica.abrirConexao();
             var comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, "FISICO");
            comando.setString(2, titulo);
            comando.setString(3, "Equipe LP II");
            comando.setString(4, "123");
            comando.executeUpdate();
            try (var chaves = comando.getGeneratedKeys()) {
                assertTrue(chaves.next());
                return chaves.getLong(1);
            }
        }
    }
}
