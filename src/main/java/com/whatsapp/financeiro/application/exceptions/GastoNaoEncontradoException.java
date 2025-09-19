package com.whatsapp.financeiro.application.exceptions;

public class GastoNaoEncontradoException extends RuntimeException {
    public GastoNaoEncontradoException() {
        super("Gasto não encontrado.");
    }
}
