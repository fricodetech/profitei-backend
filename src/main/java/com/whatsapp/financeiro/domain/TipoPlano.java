package com.whatsapp.financeiro.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoPlano {
    BASICO(1, "Basico"),
    PREMIUM(2, "Premium");

    private Integer id;
    private String nome;
}
