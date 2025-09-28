package com.whatsapp.financeiro.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Usuario {
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private LocalDate dataCriacao;
    private Plano plano;

    public void setDados(Usuario novosDados) {
        this.nome = novosDados.getNome();
        this.email = novosDados.getEmail();
        this.telefone = novosDados.getTelefone();
        this.plano = novosDados.getPlano();
    }
}
