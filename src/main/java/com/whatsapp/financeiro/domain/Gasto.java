package com.whatsapp.financeiro.domain;

import com.whatsapp.financeiro.infrastructure.repositories.entities.CategoriaEntity;
import com.whatsapp.financeiro.infrastructure.repositories.entities.ClienteEntity;
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
    private CategoriaEntity categoria;
    private ClienteEntity cliente;
}
