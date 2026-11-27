package com.imd.model.usuario;

import com.imd.enums.Perfil;

public class Bibliotecario extends Leitor {

    private static final int LIMITE_EMPRESTIMOS = 3;      // a combinar com o grupo
    private static final int PRAZO_EMPRESTIMO_DIAS = 15;  // a combinar com o grupo

    public Bibliotecario(Long id, String nome, String email, String senhaHash, String matricula) {
        super(id, nome, email, senhaHash, matricula);
        concederPerfil(Perfil.BIBLIOTECARIO);
    }

    @Override
    public int limiteEmprestimos() {
        return LIMITE_EMPRESTIMOS;
    }

    @Override
    public int prazoEmprestimo() {
        return PRAZO_EMPRESTIMO_DIAS;
    }
}
