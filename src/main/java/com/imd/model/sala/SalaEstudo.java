package com.imd.model.sala;

import com.imd.enums.StatusSala;

import java.util.ArrayList;
import java.util.List;

public class SalaEstudo {

    private Long id;
    private String identificacao;
    private int capacidade;
    private int tempoMaximoPermanencia;
    private StatusSala status;
    private final List<Equipamento> equipamentos = new ArrayList<>();
    private final List<HorarioSala> horarios = new ArrayList<>();
    private final List<ReservaSala> reservas = new ArrayList<>();

    public SalaEstudo(Long id, String identificacao, int capacidade, int tempoMaximoPermanencia) {
        this.id = id;
        this.identificacao = identificacao;
        this.capacidade = capacidade;
        this.tempoMaximoPermanencia = tempoMaximoPermanencia;
        this.status = StatusSala.LIVRE;
    }

    public StatusSala consultarStatus() {
        // TODO: Consultar o estado da sala considerando reservas e utilização.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public List<Equipamento> listarEquipamentosDisponiveis() {
        // TODO: Consultar os equipamentos disponíveis na sala.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public Long getId() {
        return id;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getTempoMaximoPermanencia() {
        return tempoMaximoPermanencia;
    }

    public StatusSala getStatus() {
        return status;
    }

    public List<Equipamento> getEquipamentos() {
        return List.copyOf(equipamentos);
    }

    public List<HorarioSala> getHorarios() {
        return List.copyOf(horarios);
    }

    public List<ReservaSala> getReservas() {
        return List.copyOf(reservas);
    }
}
