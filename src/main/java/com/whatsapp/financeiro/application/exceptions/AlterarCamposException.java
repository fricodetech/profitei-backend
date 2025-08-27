package com.whatsapp.financeiro.application.exceptions;

public class AlterarCamposException extends RuntimeException {
    public AlterarCamposException(String message, Throwable causa) {
        super(message, causa);
    }
}
