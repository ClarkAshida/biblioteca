package com.imd.ui;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleBibliotecaTest {
    private String executar(String roteiro) {
        var buffer = new ByteArrayOutputStream();
        try (var entrada = new Scanner(roteiro); var saida = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            new ConsoleBiblioteca(entrada, saida).executar();
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    @Test void navegaMenusTrataEntradasInvalidasERetorna() {
        String saida = executar("x\n2\nabc\n999\n1\n0\n1\n1\n0\n0\n");
        assertTrue(saida.contains("Opção inválida"));
        assertTrue(saida.contains("Consultar materiais e disponibilidade"));
        assertTrue(saida.contains("será conectada pelo responsável"));
        assertTrue(saida.contains("Até logo"));
    }

    @Test void terminoDaEntradaNoSubmenuNaoTravaNavegacao() {
        String saida = executar("2\n");
        assertTrue(saida.contains("Até logo"));
    }

    @Test void todosOsModulosPermitemVoltarSemExecutarFuncaoPendente() {
        String saida = executar("1\n0\n3\n0\n4\n0\n5\n0\n6\n0\n0\n");
        assertTrue(saida.contains("Login e logout"));
        assertTrue(saida.contains("Consultar fila de espera"));
        assertTrue(saida.contains("Realizar check-in"));
        assertTrue(saida.contains("Utilização dos e-books"));
    }
}
