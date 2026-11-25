package com.imd.model.emprestimo;

import com.imd.enums.StatusEmprestimo;
import com.imd.model.acervo.Material;
import com.imd.model.usuario.Leitor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Emprestimo {

    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataPrevista;
    private LocalDate dataEncerramento;
    private StatusEmprestimo status;
    private Leitor leitor;
    private final List<Multa> multas = new ArrayList<>();

    protected Emprestimo(Long id, Leitor leitor, LocalDate dataInicio) {
        this.id = id;
        this.leitor = leitor;
        this.dataInicio = dataInicio;
        this.status = StatusEmprestimo.ATIVO;
    }

    public boolean renovar() {
        // TODO: Validar e renovar o empréstimo conforme o perfil e a fila de espera.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public abstract void devolver();

    public abstract void expirar();

    public abstract double calcularMulta();

    public abstract Material getMaterial();

    public Long getId() {
        return id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    public LocalDate getDataEncerramento() {
        return dataEncerramento;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public List<Multa> getMultas() {
        return List.copyOf(multas);
    }
}
