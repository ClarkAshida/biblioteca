package com.imd.ui;

import com.imd.model.acervo.*;
import com.imd.service.AcervoService;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.*;

public final class ConsoleAcervo {
    private final Scanner entrada;
    private final PrintStream saida;
    private final AcervoService service;
    public ConsoleAcervo(Scanner entrada, PrintStream saida, AcervoService service) {
        this.entrada = entrada; this.saida = saida; this.service = service;
    }
    private String ler(String campo) {
        saida.print(campo + ": ");
        if (!entrada.hasNextLine()) throw new NoSuchElementException();
        return entrada.nextLine().strip();
    }
    public void executar() {
        while (true) {
            saida.println("""

                    Acervo e disponibilidade
                    1 - Consultar materiais e disponibilidade
                    2 - Cadastrar livro físico
                    3 - Cadastrar e-book
                    4 - Editar material
                    5 - Excluir material
                    6 - Adicionar exemplar
                    7 - Excluir exemplar
                    0 - Voltar
                    """);
            try {
                switch (ler("Opção")) {
                    case "0" -> { return; }
                    case "1" -> listar();
                    case "2" -> {
                        long id = service.cadastrarLivro(ler("Título"), ler("Autor"), ler("ISBN"), Arrays.asList(ler("Códigos dos exemplares separados por vírgula").split(",", -1)));
                        saida.println("Livro cadastrado. ID: " + id);
                    }
                    case "3" -> {
                        long id = service.cadastrarEbook(ler("Título"), ler("Autor"), ler("ISBN"), Integer.parseInt(ler("Total de licenças")));
                        saida.println("E-book cadastrado. ID: " + id);
                    }
                    case "4" -> {
                        long id = Long.parseLong(ler("ID"));
                        Material material = service.listar().stream().filter(m -> m.getId() == id).findFirst().orElseThrow(() -> new IllegalArgumentException("Material não encontrado."));
                        String titulo = ler("Título"), autor = ler("Autor"), isbn = ler("ISBN");
                        Integer total = material instanceof EBook ? Integer.valueOf(ler("Total de licenças")) : null;
                        service.editar(id, titulo, autor, isbn, total);
                        saida.println("Material atualizado.");
                    }
                    case "5" -> { service.excluir(Long.parseLong(ler("ID"))); saida.println("Material excluído."); }
                    case "6" -> { service.adicionarExemplar(Long.parseLong(ler("ID do livro")), ler("Código")); saida.println("Exemplar adicionado."); }
                    case "7" -> { service.excluirExemplar(ler("Código")); saida.println("Exemplar excluído."); }
                    default -> saida.println("Opção inválida.");
                }
            } catch (NoSuchElementException e) { return;
            } catch (IllegalArgumentException e) { saida.println("Entrada inválida: " + e.getMessage());
            } catch (SQLException e) {
                saida.println("Operação recusada pelo banco: verifique códigos duplicados ou vínculos com empréstimos e reservas.");
            }
        }
    }
    private void listar() throws SQLException {
        var materiais = service.listar();
        if (materiais.isEmpty()) saida.println("Nenhum material cadastrado.");
        for (Material m : materiais) {
            saida.printf("%d | %s | %s | ISBN %s | %s%n", m.getId(), m.getTitulo(), m.getAutor(), m.getIsbn(), m.consultarDisponibilidade() ? "Disponível" : "Indisponível");
            if (m instanceof LivroFisico livro) {
                for (Exemplar e : livro.getExemplares()) saida.printf("  Exemplar %s: %s%n", e.getCodigo(), e.isEmprestado() ? "emprestado" : "disponível");
            } else if (m instanceof EBook ebook) saida.printf("  Licenças: %d totais, %d em uso%n", ebook.getLicencasTotais(), ebook.getLicencasEmUso());
        }
    }
}
