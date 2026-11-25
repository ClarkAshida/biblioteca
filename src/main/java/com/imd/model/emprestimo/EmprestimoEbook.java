package com.imd.model.emprestimo;

import com.imd.model.acervo.EBook;
import com.imd.model.usuario.Leitor;

import java.time.LocalDate;

public class EmprestimoEbook extends Emprestimo {

    private EBook ebook;

    public EmprestimoEbook(Long id, Leitor leitor, EBook ebook, LocalDate dataInicio) {
        super(id, leitor, dataInicio);
        this.ebook = ebook;
    }

    @Override
    public void devolver() {
        // TODO: Encerrar o empréstimo digital e liberar a licença.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    @Override
    public void expirar() {
        // TODO: Tratar o vencimento do acesso digital.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void revogar() {
        // TODO: Revogar o acesso digital e liberar a licença.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    @Override
    public double calcularMulta() {
        // TODO: Implementar o comportamento de multa dos e-books.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public EBook getEbook() {
        return ebook;
    }

    @Override
    public EBook getMaterial() {
        return ebook;
    }
}
