package com.whatsapp.financeiro.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoOperacao {
    GANHO(1, "Ganho"),
    GASTO(2, "Gasto");

    private final Integer id;
    private final String nome;
}
