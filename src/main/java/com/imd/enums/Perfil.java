package com.imd.enums;

import java.util.EnumSet;
import java.util.Set;

public enum Perfil {
    LEITOR(EnumSet.of(
        Permissao.CONSULTAR_ACERVO, Permissao.CONSULTAR_PROPRIOS_EMPRESTIMOS,
        Permissao.RENOVAR_EMPRESTIMO, Permissao.RESERVAR_MATERIAL,
        Permissao.CANCELAR_RESERVA_MATERIAL, Permissao.CONSULTAR_SALAS,
        Permissao.RESERVAR_SALA, Permissao.CANCELAR_RESERVA_SALA,
        Permissao.CHECK_IN, Permissao.CHECK_OUT)),
    BIBLIOTECARIO(EnumSet.allOf(Permissao.class));

    private final Set<Permissao> permissoes;

    Perfil(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public boolean permite(Permissao permissao) {
        return permissoes.contains(permissao);
    }
}
