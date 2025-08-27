package com.whatsapp.financeiro.entrypoint.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class CategoriaDto {
    private UUID id;

    @NotBlank(message = "O titulo não pode ser vazio.")
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres.")
    private String titulo;

    @Size(max = 255, message = "A descrição pode ter no máximo 255 caracteres.")
    private String descricao;

    @NotNull(message = "O cliente não pode ser nulo.")
    private ClienteDto cliente;
}
