package com.whatsapp.financeiro.infrastructure.mapper;

import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.infrastructure.repository.entities.OperacaoEntity;

public class OperacaoMapperInfra {

    public static OperacaoEntity paraEntity(Operacao domain) {
        return OperacaoEntity.builder()
                .id(domain.getId())
                .descricao(domain.getDescricao())
                .tipoOperacao(domain.getTipoOperacao())
                .valor(domain.getValor())
                .dataOperacao(domain.getDataOperacao())
                .categoria(CategoriaMapperInfra.paraEntity(domain.getCategoria()))
                .usuario(UsuarioMapperInfra.paraEntity(domain.getUsuario()))
                .build();
    }

    public static Operacao paraDomain(OperacaoEntity entity) {
        return Operacao.builder()
                .id(entity.getId())
                .descricao(entity.getDescricao())
                .tipoOperacao(entity.getTipoOperacao())
                .valor(entity.getValor())
                .dataOperacao(entity.getDataOperacao())
                .categoria(CategoriaMapperInfra.paraDomain(entity.getCategoria()))
                .usuario(UsuarioMapperInfra.paraDomain(entity.getUsuario()))
                .build();
    }
}
