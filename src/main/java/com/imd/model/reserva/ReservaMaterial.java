package com.imd.model.reserva;

import com.imd.enums.StatusReservaMaterial;
import com.imd.model.acervo.Material;
import com.imd.model.usuario.Leitor;

import java.time.LocalDateTime;

public class ReservaMaterial {

    private Long id;
    private LocalDateTime dataSolicitacao;
    private int posicaoFila;
    private LocalDateTime prazoRetirada;
    private StatusReservaMaterial status;
    private Leitor leitor;
    private Material material;

    public ReservaMaterial(Long id, Leitor leitor, Material material, LocalDateTime dataSolicitacao, int posicaoFila) {
        this.id = id;
        this.leitor = leitor;
        this.material = material;
        this.dataSolicitacao = dataSolicitacao;
        this.posicaoFila = posicaoFila;
        this.status = StatusReservaMaterial.AGUARDANDO;
    }

    public void disponibilizar() {
        // TODO: Disponibilizar o material respeitando a fila FIFO e definir o prazo de retirada.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void concluir() {
        // TODO: Concluir a reserva após a retirada do material.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void expirar() {
        // TODO: Expirar a oportunidade de retirada e encaminhar a fila.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void cancelar() {
        // TODO: Validar e cancelar a reserva do material.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public int getPosicaoFila() {
        return posicaoFila;
    }

    public LocalDateTime getPrazoRetirada() {
        return prazoRetirada;
    }

    public StatusReservaMaterial getStatus() {
        return status;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public Material getMaterial() {
        return material;
    }
}
