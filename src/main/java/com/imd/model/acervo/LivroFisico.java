package com.imd.model.acervo;

import com.imd.model.emprestimo.EmprestimoFisico;
import com.imd.model.usuario.Leitor;

import java.util.ArrayList;
import java.util.List;

public class LivroFisico extends Material {

    private final List<Exemplar> exemplares = new ArrayList<>();

    public LivroFisico(Long id, String titulo, String autor, String isbn) {
        super(id, titulo, autor, isbn);
    }

    @Override
    public boolean consultarDisponibilidade() {
        return exemplares.stream().anyMatch(exemplar -> !exemplar.isEmprestado());
    }

    @Override
    public EmprestimoFisico criarEmprestimo(Leitor leitor) {
        // TODO: Validar o leitor e criar um empréstimo físico.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public List<Exemplar> getExemplares() {
        return List.copyOf(exemplares);
    }

    public void adicionarExemplar(Exemplar exemplar) {
        if (exemplar == null || exemplar.getLivro() != this) {
            throw new IllegalArgumentException("O exemplar deve pertencer a este livro.");
        }
        if (exemplares.stream().anyMatch(e -> e.getCodigo().equals(exemplar.getCodigo()))) {
            throw new IllegalArgumentException("Código de exemplar duplicado.");
        }
        exemplares.add(exemplar);
    }
}
