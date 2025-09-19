package com.whatsapp.financeiro.infrastructure.mapper;

import com.whatsapp.financeiro.domain.Gasto;
import com.whatsapp.financeiro.infrastructure.repository.entities.GastoEntity;

public class GastoMapperInfra {

    public static GastoEntity paraEntity(Gasto domain) {
        return GastoEntity.builder()
                .id(domain.getId())
                .valor(domain.getValor())
                .dataGasto(domain.getDataGasto())
                .categoria(CategoriaMapperInfra.paraEntity(domain.getCategoria()))
                .usuario(UsuarioMapperInfra.paraEntity(domain.getUsuario()))
                .build();
    }

    public static Gasto paraDomain(GastoEntity entity) {
        return Gasto.builder()
                .id(entity.getId())
                .valor(entity.getValor())
                .dataGasto(entity.getDataGasto())
                .categoria(CategoriaMapperInfra.paraDomain(entity.getCategoria()))
                .usuario(UsuarioMapperInfra.paraDomain(entity.getUsuario()))
                .build();
    }
}
