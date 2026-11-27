package com.imd.model.usuario;

public class Professor extends Leitor {

    private static final int LIMITE_EMPRESTIMOS = 5;
    private static final int PRAZO_EMPRESTIMO_DIAS = 30;

    private String departamento;

    public Professor(Long id, String nome, String email, String senha, String matricula, String departamento) {
        super(id, nome, email, senha, matricula);
        this.departamento = departamento;
    }

    @Override
    public int limiteEmprestimos() {
        // TODO: Definir o limite de empréstimos para professores.
        return LIMITE_EMPRESTIMOS;
    }

    @Override
    public int prazoEmprestimo() {
        // TODO: Definir o prazo de empréstimo para professores.
        return PRAZO_EMPRESTIMO_DIAS;
    }

    public String getDepartamento() {
        return departamento;
    }
}
