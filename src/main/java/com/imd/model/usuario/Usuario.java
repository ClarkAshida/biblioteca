package com.imd.model.usuario;

import com.imd.enums.Perfil;

public abstract class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Perfil perfil;

    protected Usuario(Long id, String nome, String email, String senha, Perfil perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    public boolean autenticar(String senha) {
        // TODO: Implementar autenticação e proteção de senhas.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public boolean temPermissao(String operacao) {
        // TODO: Implementar as permissões de cada perfil.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }
}
