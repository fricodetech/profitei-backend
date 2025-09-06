package com.whatsapp.financeiro.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPlano {
    ATIVO(1, "Ativo"),
    INATIVO(2, "Inativo");

    private Integer id;
    private String nome;
}
