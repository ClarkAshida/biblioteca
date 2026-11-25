package com.imd.model.emprestimo;

import com.imd.enums.StatusMulta;

public class Multa {

    private Long id;
    private double valor;
    private int diasAtraso;
    private StatusMulta status;

    public Multa(Long id, double valor, int diasAtraso) {
        this.id = id;
        this.valor = valor;
        this.diasAtraso = diasAtraso;
        this.status = StatusMulta.PENDENTE;
    }

    public void quitar() {
        // TODO: Validar e registrar a quitação da multa.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public Long getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public StatusMulta getStatus() {
        return status;
    }
}
