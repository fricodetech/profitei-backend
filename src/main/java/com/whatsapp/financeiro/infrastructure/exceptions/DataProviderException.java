package com.whatsapp.financeiro.infrastructure.exceptions;

public class DataProviderException extends RuntimeException {
    public DataProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
