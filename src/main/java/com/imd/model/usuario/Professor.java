package com.imd.model.usuario;

import com.imd.exception.DadosInvalidosException;

public class Professor extends Leitor {

    private static final int LIMITE_EMPRESTIMOS = 5;
    private static final int PRAZO_EMPRESTIMO_DIAS = 30;

    private String departamento;

    public Professor(Long id, String nome, String email, String senhaHash, String matricula, String departamento) {
        super(id, nome, email, senhaHash, matricula);
        if (departamento == null || departamento.isBlank()) {
            throw new DadosInvalidosException("O departamento é obrigatório.");
        }
        this.departamento = departamento;
    }

    @Override
    public int limiteEmprestimos() {
        return LIMITE_EMPRESTIMOS;
    }

    @Override
    public int prazoEmprestimo() {
        return PRAZO_EMPRESTIMO_DIAS;
    }

    public String getDepartamento() {
        return departamento;
    }
}
