package com.imd.infraestrutura.banco;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public final class FabricaConexoes {
    private final String url;
    private final String usuario;
    private final String senha;

    public FabricaConexoes(String url, String usuario, String senha) {
        this.url = Objects.requireNonNull(url, "URL JDBC obrigatória");
        this.usuario = Objects.requireNonNull(usuario, "Usuário JDBC obrigatório");
        this.senha = Objects.requireNonNull(senha, "Senha JDBC obrigatória; pode ser vazia");
    }

    public static FabricaConexoes padrao() {
        var propriedades = new Properties();
        try (var recurso = FabricaConexoes.class.getResourceAsStream("/banco.properties")) {
            if (recurso == null) {
                throw new IllegalStateException("Arquivo banco.properties não encontrado no classpath.");
            }
            try (var leitor = new InputStreamReader(recurso, StandardCharsets.UTF_8)) {
                propriedades.load(leitor);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível ler banco.properties.", e);
        }

        return new FabricaConexoes(
                propriedades.getProperty("biblioteca.jdbc.url"),
                propriedades.getProperty("biblioteca.jdbc.usuario"),
                propriedades.getProperty("biblioteca.jdbc.senha"));
    }

    /** Retorna uma nova conexão com o autoCommit padrão do JDBC habilitado. */
    public Connection abrirConexao() throws SQLException {
        return DriverManager.getConnection(url, usuario, senha);
    }
}
