package com.whatsapp.financeiro.domain;

import lombok.*;

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

    public void setDados(Usuario novosDados) {
        this.nome = novosDados.getNome();
        this.email = novosDados.getEmail();
        this.telefone = novosDados.getTelefone();
    }
}
