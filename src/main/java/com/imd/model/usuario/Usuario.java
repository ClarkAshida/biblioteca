package com.imd.model.usuario;

import com.imd.enums.Perfil;
import com.imd.enums.Permissao;
import com.imd.exception.DadosInvalidosException;
import com.imd.infraestrutura.seguranca.GeradorHash;

public abstract class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senhaHash;
    private Perfil perfil;

    protected Usuario(Long id, String nome, String email, String senhaHash, Perfil perfil) {
        if (nome == null || nome.isBlank()) {
            throw new DadosInvalidosException("O nome é obrigatório.");
        }
        if (email == null || !email.contains("@")) {
            throw new DadosInvalidosException("E-mail inválido.");
        }
        if (senhaHash == null || senhaHash.isBlank()) {
            throw new DadosInvalidosException("A senha é obrigatória.");
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.perfil = perfil;
    }

    public boolean autenticar(String senha) {
        return senha != null && GeradorHash.verificar(senha, senhaHash);
    }

    public boolean temPermissao(Permissao permissao) {
        return perfil.permite(permissao);
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

    public String getSenhaHash() {
        return senhaHash;
    }

    public Perfil getPerfil() {
        return perfil;
    }
}
