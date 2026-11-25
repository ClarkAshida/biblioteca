package com.imd.infraestrutura.banco;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FabricaConexoesTest {
    @TempDir
    Path diretorioTemporario;

    @Test
    void gravaEConsultaDadosComPreparedStatement() throws SQLException {
        try (var conexao = bancoEmMemoria().abrirConexao()) {
            criarTabela(conexao);
            inserir(conexao, "Introdução à programação");

            try (var consulta = conexao.prepareStatement("SELECT titulo FROM material_teste WHERE id = ?")) {
                consulta.setLong(1, 1L);
                try (var resultado = consulta.executeQuery()) {
                    assertTrue(resultado.next());
                    assertEquals("Introdução à programação", resultado.getString("titulo"));
                }
            }
        }
    }

    @Test
    void rollbackDesfazInsercaoEPermiteNovoCommit() throws SQLException {
        try (var conexao = bancoEmMemoria().abrirConexao()) {
            criarTabela(conexao);
            conexao.setAutoCommit(false);
            inserir(conexao, "Empréstimo cancelado");
            conexao.rollback();
            assertEquals(0, contar(conexao));

            inserir(conexao, "Empréstimo confirmado");
            conexao.commit();
            assertEquals(1, contar(conexao));
        }
    }

    @Test
    void bancoEmArquivoPreservaDadosAposFecharTodasAsConexoes() throws SQLException {
        var caminho = diretorioTemporario.resolve("biblioteca").toAbsolutePath().toString().replace('\\', '/');
        var fabrica = new FabricaConexoes("jdbc:h2:file:" + caminho, "sa", "");

        try (var conexao = fabrica.abrirConexao()) {
            criarTabela(conexao);
            inserir(conexao, "Material persistido");
        }
        try (var conexao = fabrica.abrirConexao()) {
            assertEquals(1, contar(conexao));
        }
    }

    @Test
    void bancosEmMemoriaDistintosNaoCompartilhamDados() throws SQLException {
        try (var primeira = bancoEmMemoria().abrirConexao();
             var segunda = bancoEmMemoria().abrirConexao()) {
            criarTabela(primeira);
            criarTabela(segunda);
            inserir(primeira, "Material do primeiro banco");

            assertEquals(1, contar(primeira));
            assertEquals(0, contar(segunda));
        }
    }

    private FabricaConexoes bancoEmMemoria() {
        return new FabricaConexoes("jdbc:h2:mem:teste_" + UUID.randomUUID(), "sa", "");
    }

    private void criarTabela(Connection conexao) throws SQLException {
        try (var comando = conexao.createStatement()) {
            comando.execute("CREATE TABLE material_teste (id BIGINT PRIMARY KEY, titulo VARCHAR(255) NOT NULL)");
        }
    }

    private void inserir(Connection conexao, String titulo) throws SQLException {
        try (var comando = conexao.prepareStatement("INSERT INTO material_teste (id, titulo) VALUES (?, ?)")) {
            comando.setLong(1, 1L);
            comando.setString(2, titulo);
            comando.executeUpdate();
        }
    }

    private long contar(Connection conexao) throws SQLException {
        try (var comando = conexao.createStatement();
             var resultado = comando.executeQuery("SELECT COUNT(*) FROM material_teste")) {
            resultado.next();
            return resultado.getLong(1);
        }
    }
}
