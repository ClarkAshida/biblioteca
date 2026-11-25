package com.imd.model.usuario;

import com.imd.enums.Perfil;
import com.imd.model.emprestimo.Emprestimo;
import com.imd.model.reserva.ReservaMaterial;
import com.imd.model.sala.ReservaSala;

import java.util.ArrayList;
import java.util.List;

public abstract class Leitor extends Usuario {

    private String matricula;
    private final List<Emprestimo> emprestimos = new ArrayList<>();
    private final List<ReservaMaterial> reservasMaterial = new ArrayList<>();
    private final List<ReservaSala> reservasSala = new ArrayList<>();

    protected Leitor(Long id, String nome, String email, String senha, String matricula) {
        super(id, nome, email, senha, Perfil.LEITOR);
        this.matricula = matricula;
    }

    public abstract int limiteEmprestimos();

    public abstract int prazoEmprestimo();

    public boolean possuiMultasPendentes() {
        // TODO: Consultar as multas pendentes do leitor.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public String getMatricula() {
        return matricula;
    }

    public List<Emprestimo> getEmprestimos() {
        return List.copyOf(emprestimos);
    }

    public List<ReservaMaterial> getReservasMaterial() {
        return List.copyOf(reservasMaterial);
    }

    public List<ReservaSala> getReservasSala() {
        return List.copyOf(reservasSala);
    }
}
