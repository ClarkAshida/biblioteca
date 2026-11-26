package com.imd.ui;

import java.io.PrintStream;
import java.util.Scanner;

public final class ConsoleBiblioteca {
    private final Scanner entrada;
    private final PrintStream saida;
    private final com.imd.service.AcervoService acervo;

    public ConsoleBiblioteca(Scanner entrada, PrintStream saida) {
        this(entrada, saida, new com.imd.service.AcervoService(new com.imd.repository.AcervoRepository(
                com.imd.infraestrutura.banco.FabricaConexoes.padrao())));
    }

    public ConsoleBiblioteca(Scanner entrada, PrintStream saida, com.imd.service.AcervoService acervo) {
        this.entrada = entrada;
        this.saida = saida;
        this.acervo = acervo;
    }

    public void executar() {
        saida.println("Sistema de Biblioteca — base inicial LP II");
        while (true) {
            saida.println("""

                    1 - Usuários e acesso
                    2 - Acervo e disponibilidade
                    3 - Empréstimos e multas
                    4 - Reservas de materiais
                    5 - Salas de estudo
                    6 - Relatórios
                    0 - Sair
                    """);
            switch (lerOpcao()) {
                case "0" -> { saida.println("Até logo!"); return; }
                case "1" -> menuPendente("Usuários e acesso", "Login e logout", "Gestão de usuários (CRUD)", "Permissões por perfil");
                case "2" -> new ConsoleAcervo(entrada, saida, acervo).executar();
                case "3" -> menuPendente("Empréstimos e multas", "Emprestar material", "Devolver livro físico",
                        "Revogar acesso de e-book", "Renovar empréstimo", "Consultar empréstimos e multas", "Quitar multa");
                case "4" -> menuPendente("Reservas de materiais", "Solicitar reserva", "Consultar fila de espera", "Cancelar reserva");
                case "5" -> menuPendente("Salas de estudo", "Gestão de salas e equipamentos (CRUD)", "Consultar status e equipamentos",
                        "Reservar sala", "Realizar check-in", "Realizar check-out", "Cancelar reserva");
                case "6" -> menuPendente("Relatórios", "Atrasos e multas", "Ocupação das salas", "Utilização dos e-books");
                default -> saida.println("Opção inválida. Escolha uma das opções exibidas.");
            }
        }
    }

    private void menuPendente(String titulo, String... operacoes) {
        while (true) {
            saida.println("\n" + titulo);
            for (int i = 0; i < operacoes.length; i++) {
                saida.printf("%d - %s (em desenvolvimento)%n", i + 1, operacoes[i]);
            }
            saida.println("0 - Voltar");
            String opcao = lerOpcao();
            if (opcao.equals("0")) return;
            try {
                int indice = Integer.parseInt(opcao) - 1;
                if (indice < 0 || indice >= operacoes.length) saida.println("Opção inválida.");
                else saida.println("Esta funcionalidade será conectada pelo responsável pelo módulo.");
            } catch (NumberFormatException e) {
                saida.println("Opção inválida.");
            }
        }
    }

    private String lerOpcao() {
        saida.print("Opção: ");
        return entrada.hasNextLine() ? entrada.nextLine().strip() : "0";
    }
}
