package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.domain.Lembrete;
import com.whatsapp.financeiro.entrypoint.dto.LembreteDto;

public class LembreteMapperEntry {

    public static Lembrete paraDomain(LembreteDto dto) {
        return Lembrete.builder()
                .id(dto.getId())
                .titulo(dto.getTitulo())
                .dataCriacao(dto.getDataCriacao())
                .dataExecucao(dto.getDataExecucao())
                .repete(dto.getRepete())
                .diasRepeticao(dto.getDiasRepeticao())
                .horario(dto.getHorario())
                .build();
    }

    public static LembreteDto paraDto(Lembrete domain) {
        return LembreteDto.builder()
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
