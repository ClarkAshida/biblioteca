package com.imd.model.usuario;

import com.imd.enums.Perfil;
import com.imd.enums.Permissao;
import com.imd.exception.AcessoNegadoException;
import com.imd.exception.DadosInvalidosException;
import com.imd.infraestrutura.seguranca.GeradorHash;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public abstract class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senhaHash;
    private final Set<Perfil> perfis = EnumSet.noneOf(Perfil.class);

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
        if (perfil == null) {
            throw new DadosInvalidosException("O perfil é obrigatório.");
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.perfis.add(perfil);
    }

    public boolean autenticar(String senha) {
        return senha != null && GeradorHash.verificar(senha, senhaHash);
    }

    public final void concederPerfil(Perfil perfil) {
        if (perfil == null) {
            throw new DadosInvalidosException("O perfil é obrigatório.");
        }
        perfis.add(perfil);
    }

    public boolean temPerfil(Perfil perfil) {
        return perfis.contains(perfil);
    }

    public boolean temPermissao(Permissao permissao) {
        return perfis.stream().anyMatch(p -> p.permite(permissao));
    }

    public void exigirPermissao(Permissao permissao) {
        if (!temPermissao(permissao)) {
            throw new AcessoNegadoException("Você não tem permissão para: " + permissao);
        }
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

    public Set<Perfil> getPerfis() {
        return Collections.unmodifiableSet(perfis);
    }
}
