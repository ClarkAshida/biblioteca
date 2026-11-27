package com.imd.model.usuario;

public class Aluno extends Leitor {

    private static final int LIMITE_EMPRESTIMOS = 3;
    private static final int PRAZO_EMPRESTIMO_DIAS = 15;

    private String curso;

    public Aluno(Long id, String nome, String email, String senha, String matricula, String curso) {
        super(id, nome, email, senha, matricula);
        this.curso = curso;
    }

    @Override
    public int limiteEmprestimos() {
        // TODO: Definir o limite de empréstimos para alunos.
        return LIMITE_EMPRESTIMOS;
    }

    @Override
    public int prazoEmprestimo() {
        // TODO: Definir o prazo de empréstimo para alunos.
        return PRAZO_EMPRESTIMO_DIAS;
    }

    public String getCurso() {
        return curso;
    }
}
