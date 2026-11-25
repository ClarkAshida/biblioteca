package com.imd.model.usuario;

public class Professor extends Leitor {

    private String departamento;

    public Professor(Long id, String nome, String email, String senha, String matricula, String departamento) {
        super(id, nome, email, senha, matricula);
        this.departamento = departamento;
    }

    @Override
    public int limiteEmprestimos() {
        // TODO: Definir o limite de empréstimos para professores.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    @Override
    public int prazoEmprestimo() {
        // TODO: Definir o prazo de empréstimo para professores.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public String getDepartamento() {
        return departamento;
    }
}
