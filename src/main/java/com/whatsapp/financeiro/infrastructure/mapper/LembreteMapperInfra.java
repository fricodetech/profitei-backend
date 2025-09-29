package com.whatsapp.financeiro.infrastructure.mapper;

import com.whatsapp.financeiro.domain.Lembrete;
import com.whatsapp.financeiro.infrastructure.repository.entities.LembreteEntity;

public class LembreteMapperInfra {

    public static Lembrete paraDomain(LembreteEntity entity) {
        return Lembrete.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .dataCriacao(entity.getDataCriacao())
                .dataExecucao(entity.getDataExecucao())
                .repete(entity.getRepete())
                .diasRepeticao(entity.getDiasRepeticao())
                .horario(entity.getHorario())
                .build();
    }

    public static LembreteEntity paraEntity(Lembrete domain) {
        return LembreteEntity.builder()
                .id(domain.getId())
                .titulo(domain.getTitulo())
                .dataCriacao(domain.getDataCriacao())
                .dataExecucao(domain.getDataExecucao())
                .repete(domain.getRepete())
                .diasRepeticao(domain.getDiasRepeticao())
                .horario(domain.getHorario())
                .build();
    }
}
