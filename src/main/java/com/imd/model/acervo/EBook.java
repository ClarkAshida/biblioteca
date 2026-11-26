package com.imd.model.acervo;

import com.imd.model.emprestimo.EmprestimoEbook;
import com.imd.model.usuario.Leitor;

public class EBook extends Material {

    private int licencasTotais;
    private int licencasEmUso;

    public EBook(Long id, String titulo, String autor, String isbn, int licencasTotais) {
        this(id, titulo, autor, isbn, licencasTotais, 0);
    }

    public EBook(Long id, String titulo, String autor, String isbn, int licencasTotais, int licencasEmUso) {
        super(id, titulo, autor, isbn);
        this.licencasTotais = licencasTotais;
        this.licencasEmUso = licencasEmUso;
    }

    @Override
    public boolean consultarDisponibilidade() {
        return licencasEmUso < licencasTotais;
    }

    @Override
    public EmprestimoEbook criarEmprestimo(Leitor leitor) {
        // TODO: Validar o leitor e criar um empréstimo digital.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void revogarAcesso(EmprestimoEbook emprestimo) {
        // TODO: Revogar o acesso e atualizar as licenças.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public int getLicencasTotais() {
        return licencasTotais;
    }

    public int getLicencasEmUso() {
        return licencasEmUso;
    }
}
