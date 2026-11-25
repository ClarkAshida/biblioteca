package com.imd.infraestrutura.banco;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public final class InicializadorBanco {
    private final FabricaConexoes fabrica;

    public InicializadorBanco(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    public void inicializar() throws SQLException {
        String script;
        try (var recurso = InicializadorBanco.class.getResourceAsStream("/schema.sql")) {
            if (recurso == null) {
                throw new IllegalStateException("Arquivo schema.sql não encontrado.");
            }
            script = new String(recurso.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível ler schema.sql.", e);
        }

        try (var conexao = fabrica.abrirConexao(); var comando = conexao.createStatement()) {
            for (String sql : script.split(";")) {
                if (!sql.isBlank()) {
                    comando.execute(sql);
                }
            }
        }
    }
}
