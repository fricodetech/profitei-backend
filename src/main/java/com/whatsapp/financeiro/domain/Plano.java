package com.whatsapp.financeiro.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@ToString
@Builder
public class Plano {
    private UUID id;
    private StatusPlano statusPlano;
    private TipoPlano tipoPlano;
    private BigDecimal valor;
}
