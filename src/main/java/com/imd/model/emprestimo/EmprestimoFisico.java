package com.imd.model.emprestimo;

import com.imd.model.acervo.Exemplar;
import com.imd.model.acervo.LivroFisico;
import com.imd.model.usuario.Leitor;

import java.time.LocalDate;

public class EmprestimoFisico extends Emprestimo {

    private Exemplar exemplar;

    public EmprestimoFisico(Long id, Leitor leitor, Exemplar exemplar, LocalDate dataInicio) {
        super(id, leitor, dataInicio);
        this.exemplar = exemplar;
    }

    @Override
    public void devolver() {
        // TODO: Registrar a devolução, liberar o exemplar e apurar eventual multa.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    @Override
    public void expirar() {
        // TODO: Definir o tratamento de empréstimos físicos vencidos.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    @Override
    public double calcularMulta() {
        // TODO: Calcular a multa progressiva por atraso.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    @Override
    public LivroFisico getMaterial() {
        return exemplar.getLivro();
    }
}
