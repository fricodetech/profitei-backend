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
public class Gasto {
    private UUID id;
    private BigDecimal valor;
    private LocalDate dataGasto;
    private Categoria categoria;
    private Usuario usuario;

    public void alterarDados(Gasto novo) {
        this.valor = novo.getValor();
        this.dataGasto = novo.getDataGasto();
        this.categoria = novo.getCategoria();
    }
}
