package com.whatsapp.financeiro.application.exceptions;

public class UsuarioJaCadastradoException extends RuntimeException {
    public UsuarioJaCadastradoException() {
        super("Usuário já cadastrado com este telefone.");
    }
}
