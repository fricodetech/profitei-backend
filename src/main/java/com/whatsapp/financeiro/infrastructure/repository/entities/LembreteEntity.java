package com.whatsapp.financeiro.infrastructure.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "Lembrete")
@Table(name = "lembretes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LembreteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titulo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_execucao")
    private LocalDateTime dataExecucao;

    private Boolean repete;

    @Column(name = "data_repeticao")
    private List<DayOfWeek> diasRepeticao;

    private LocalTime horario;
}
