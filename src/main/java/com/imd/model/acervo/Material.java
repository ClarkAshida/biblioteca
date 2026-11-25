package com.imd.model.acervo;

import com.imd.model.emprestimo.Emprestimo;
import com.imd.model.reserva.ReservaMaterial;
import com.imd.model.usuario.Leitor;

import java.util.ArrayList;
import java.util.List;

public abstract class Material {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private final List<ReservaMaterial> reservas = new ArrayList<>();

    protected Material(Long id, String titulo, String autor, String isbn) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
    }

    public abstract boolean consultarDisponibilidade();

    public abstract Emprestimo criarEmprestimo(Leitor leitor);

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public List<ReservaMaterial> getReservas() {
        return List.copyOf(reservas);
    }
}
