package com.imd;

import com.imd.infraestrutura.banco.FabricaConexoes;
import com.imd.infraestrutura.banco.InicializadorBanco;
import com.imd.ui.ConsoleBiblioteca;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var fabrica = FabricaConexoes.padrao();
        var banco = new InicializadorBanco(fabrica);
        try {
            banco.inicializar();
        } catch (SQLException e) {
            throw new IllegalStateException("Não foi possível inicializar o banco. Verifique banco.properties.", e);
        }
        try (var entrada = new Scanner(System.in, StandardCharsets.UTF_8)) {
            new ConsoleBiblioteca(entrada, System.out).executar();
        }
    }
}
