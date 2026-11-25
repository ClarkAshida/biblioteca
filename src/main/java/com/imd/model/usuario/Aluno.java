package com.imd.model.usuario;

public class Aluno extends Leitor {

    private String curso;

    public Aluno(Long id, String nome, String email, String senha, String matricula, String curso) {
        super(id, nome, email, senha, matricula);
        this.curso = curso;
    }

    @Override
    public int limiteEmprestimos() {
        // TODO: Definir o limite de empréstimos para alunos.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    @Override
    public int prazoEmprestimo() {
        // TODO: Definir o prazo de empréstimo para alunos.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public String getCurso() {
        return curso;
    }
}
