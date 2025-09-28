package com.whatsapp.financeiro.domain;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@ToString
@Builder
public class Categoria {
    private UUID id;
    private String titulo;
    private String descricao;
    private Usuario usuario;

    public void alterarDados(Categoria novo) {
        this.setDescricao(novo.getDescricao());
        this.setTitulo(novo.getTitulo());
    }
}
