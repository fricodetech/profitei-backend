package com.whatsapp.financeiro.domain;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Lembrete {
    private UUID id;
    private String titulo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataExecucao;
    private Boolean repete;
    private List<DayOfWeek> diasRepeticao;
    private LocalTime horario;
}
