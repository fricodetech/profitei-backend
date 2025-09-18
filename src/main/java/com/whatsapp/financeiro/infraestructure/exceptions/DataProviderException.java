package com.whatsapp.financeiro.infraestructure.exceptions;

public class DataProviderException extends RuntimeException {
    public DataProviderException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
