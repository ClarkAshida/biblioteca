package com.imd.model.usuario;

import com.imd.enums.Perfil;
import com.imd.model.emprestimo.Emprestimo;
import com.imd.model.emprestimo.Multa;

public class Bibliotecario extends Usuario {

    public Bibliotecario(Long id, String nome, String email, String senha) {
        super(id, nome, email, senha, Perfil.BIBLIOTECARIO);
    }

    public void gerenciarAcervo() {
        // TODO: Implementar o gerenciamento do acervo.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void gerenciarUsuarios() {
        // TODO: Implementar o gerenciamento de usuários.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void gerenciarSalas() {
        // TODO: Implementar o gerenciamento de salas.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void registrarDevolucao(Emprestimo emprestimo) {
        // TODO: Implementar o registro de devolução.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }

    public void quitarMulta(Multa multa) {
        // TODO: Implementar a quitação de multa.
        throw new UnsupportedOperationException("Método ainda não implementado.");
    }
}
