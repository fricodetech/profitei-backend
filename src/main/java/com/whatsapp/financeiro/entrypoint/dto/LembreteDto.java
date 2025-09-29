package com.whatsapp.financeiro.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LembreteDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("titulo")
    @NotBlank
    private String titulo;

    @JsonProperty(namespace = "data_criacao")
    private LocalDateTime dataCriacao;

    @JsonProperty("data_execucao")
    @NotBlank
    private LocalDateTime dataExecucao;

    @JsonProperty("repete")
    @NotBlank
    private Boolean repete;

    @JsonProperty("dias_repeticao")
    @NotBlank
    private List<DayOfWeek> diasRepeticao;

    @JsonProperty("horario")
    @NotBlank
    private LocalTime horario;
}
