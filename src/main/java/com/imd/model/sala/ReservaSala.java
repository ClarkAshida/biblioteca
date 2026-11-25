package com.imd.model.sala;

import com.imd.enums.StatusReservaSala;
import com.imd.model.usuario.Leitor;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaSala {

    private Long id;
    private LocalDate data;
    private LocalTime horaCheckIn;
    private LocalTime horaCheckOut;
    private StatusReservaSala status;
    private Leitor leitor;
    private SalaEstudo sala;
    private HorarioSala horario;

    public ReservaSala(Long id, Leitor leitor, SalaEstudo sala, LocalDate data, HorarioSala horario) {
        this.id = id;
        this.leitor = leitor;
        this.sala = sala;
        this.data = data;
        this.horario = horario;
        this.status = StatusReservaSala.RESERVADA;
    }

    public void realizarCheckIn() {
        // TODO: Validar a reserva e o horário e registrar o início da utilização.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void realizarCheckOut() {
        // TODO: Registrar a saída e finalizar a utilização da sala.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void cancelar() {
        // TODO: Cancelar apenas reservas que ainda não estão em uso.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void expirar() {
        // TODO: Expirar reservas não utilizadas após o horário permitido.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public Long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHoraCheckIn() {
        return horaCheckIn;
    }

    public LocalTime getHoraCheckOut() {
        return horaCheckOut;
    }

    public StatusReservaSala getStatus() {
        return status;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public SalaEstudo getSala() {
        return sala;
    }

    public HorarioSala getHorario() {
        return horario;
    }
}
