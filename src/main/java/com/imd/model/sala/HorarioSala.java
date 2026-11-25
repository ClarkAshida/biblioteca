package com.imd.model.sala;

import java.time.LocalTime;

public class HorarioSala {

    private LocalTime inicio;
    private LocalTime fim;

    public HorarioSala(LocalTime inicio, LocalTime fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFim() {
        return fim;
    }
}
