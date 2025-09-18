package com.whatsapp.financeiro.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPlano {
    ATIVO(1, "Ativo"),
    INATIVO(2, "Inativo");

    private final Integer id;
    private final String nome;
}
