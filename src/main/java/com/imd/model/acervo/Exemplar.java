package com.imd.model.acervo;

public class Exemplar {

    private String codigo;
    private boolean emprestado;
    private LivroFisico livro;

    public Exemplar(String codigo, LivroFisico livro) {
        this(codigo, livro, false);
    }

    public Exemplar(String codigo, LivroFisico livro, boolean emprestado) {
        this.codigo = codigo;
        this.livro = livro;
        this.emprestado = emprestado;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean getEmprestado() {
        return emprestado;
    }

    public LivroFisico getLivro() {
        return livro;
    }

    public boolean isEmprestado() {
        return emprestado;
    }
}
