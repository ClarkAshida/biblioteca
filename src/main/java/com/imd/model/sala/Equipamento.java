package com.imd.model.sala;

public class Equipamento {

    private String nome;
    private boolean disponivel;

    public Equipamento(String nome, boolean disponivel) {
        this.nome = nome;
        this.disponivel = disponivel;
    }

    public String getNome() {
        return nome;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}
