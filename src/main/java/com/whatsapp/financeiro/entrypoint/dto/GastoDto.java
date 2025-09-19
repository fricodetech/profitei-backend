package com.whatsapp.financeiro.entrypoint.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GastoDto {

    private UUID id;

    @NotNull(message = "O valor do gasto é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor do gasto deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "A data do gasto é obrigatória")
    @PastOrPresent(message = "A data do gasto não pode ser no futuro")
    private LocalDate dataGasto;

    @NotNull(message = "A categoria é obrigatória")
    private CategoriaDto categoria;

    @NotNull(message = "O usuário é obrigatório")
    private UsuarioDto usuario;
}
