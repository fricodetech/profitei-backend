package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.entrypoint.dto.OperacaoDto;

public class OperacaoMapperEntry {
    public static OperacaoDto paraDto(Operacao domain) {
        return OperacaoDto.builder()
                .id(domain.getId())
                .descricao(domain.getDescricao())
                .tipoOperacao(domain.getTipoOperacao())
                .valor(domain.getValor())
                .dataOperacao(domain.getDataOperacao())
                .categoria(CategoriaMapperEntry.paraDto(domain.getCategoria()))
                .usuario(UsuarioMapperEntry.paraDto(domain.getUsuario()))
                .build();
    }

    public static Operacao paraDomain(OperacaoDto dto) {
        return Operacao.builder()
                .id(dto.getId())
                .descricao(dto.getDescricao())
                .tipoOperacao(dto.getTipoOperacao())
                .valor(dto.getValor())
                .dataOperacao(dto.getDataOperacao())
                .categoria(CategoriaMapperEntry.paraDomain(dto.getCategoria()))
                .usuario(UsuarioMapperEntry.paraDomain(dto.getUsuario()))
                .build();
    }
}
