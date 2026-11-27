package com.imd.model.usuario;

import com.imd.exception.DadosInvalidosException;

public class Aluno extends Leitor {

    private static final int LIMITE_EMPRESTIMOS = 3;
    private static final int PRAZO_EMPRESTIMO_DIAS = 15;

    private String curso;

    public Aluno(Long id, String nome, String email, String senhaHash, String matricula, String curso) {
        super(id, nome, email, senhaHash, matricula);
        if (curso == null || curso.isBlank()) {
            throw new DadosInvalidosException("O curso é obrigatório.");
        }
        this.curso = curso;
    }

    @Override
    public int limiteEmprestimos() {
        return LIMITE_EMPRESTIMOS;
    }

    @Override
    public int prazoEmprestimo() {
        return PRAZO_EMPRESTIMO_DIAS;
    }

    public String getCurso() {
        return curso;
    }
}
