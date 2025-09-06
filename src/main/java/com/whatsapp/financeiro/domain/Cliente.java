package com.whatsapp.financeiro.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@ToString
@Builder
public class Cliente {
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private LocalDate dataCriacao;
    private Plano plano;
}
