package com.whatsapp.financeiro.application.exceptions;

public class OperacaoNaoEncontradaException extends RuntimeException {
    public OperacaoNaoEncontradaException() {
        super("Operação não encontrada.");
    }
}
