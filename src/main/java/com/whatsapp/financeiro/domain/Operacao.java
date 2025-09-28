package com.whatsapp.financeiro.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class Operacao {
    private UUID id;
    private String descricao;
    private TipoOperacao tipoOperacao;
    private BigDecimal valor;
    private LocalDate dataOperacao;
    private Categoria categoria;
    private Usuario usuario;

    public void alterarDados(Operacao novo) {
        this.descricao = novo.getDescricao();
        this.valor = novo.getValor();
        this.dataOperacao = novo.getDataOperacao();
        this.categoria = novo.getCategoria();
    }
}
