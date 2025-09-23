package com.whatsapp.financeiro.infrastructure.mapper;

import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;

public class CategoriaMapperInfra {

    public static CategoriaEntity paraEntity(Categoria domain) {
        return CategoriaEntity.builder()
                .id(domain.getId())
                .titulo(domain.getTitulo())
                .descricao(domain.getDescricao())
                .usuario(UsuarioMapperInfra.paraEntity(domain.getUsuario()))
                .build();
    }

    public static Categoria paraDomain(CategoriaEntity entity) {
        return Categoria.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .descricao(entity.getDescricao())
                .usuario(UsuarioMapperInfra.paraDomain(entity.getUsuario()))
                .build();
    }
}
