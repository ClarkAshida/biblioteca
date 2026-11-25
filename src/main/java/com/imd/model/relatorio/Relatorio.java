package com.imd.model.relatorio;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Relatorio {

    private LocalDateTime dataGeracao;

    protected Relatorio() {
        this.dataGeracao = LocalDateTime.now();
    }

    public abstract String gerar(LocalDate inicio, LocalDate fim);

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }
}
