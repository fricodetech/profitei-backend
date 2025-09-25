package com.whatsapp.financeiro.entrypoint.dto;

import com.whatsapp.financeiro.domain.TipoOperacao;
import jakarta.validation.constraints.*;
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
public class OperacaoDto {

    private UUID id;
    private String descricao;

    @NotNull(message = "O tipo de operação é obrigatório.")
    @Pattern(regexp = "GASTO|GANHO", message = "Operação inválida.")
    private TipoOperacao tipoOperacao;

    @NotNull(message = "O valor da operacao é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor da operacao deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "A data da operação é obrigatória")
    @PastOrPresent(message = "A data da operação não pode ser no futuro")
    private LocalDate dataOperacao;

    @NotNull(message = "A categoria é obrigatória")
    private CategoriaDto categoria;

    @NotNull(message = "O usuário é obrigatório")
    private UsuarioDto usuario;
}
