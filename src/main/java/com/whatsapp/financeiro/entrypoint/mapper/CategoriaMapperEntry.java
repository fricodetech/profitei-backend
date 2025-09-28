package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.entrypoint.dto.CategoriaDto;

public class CategoriaMapperEntry {

    public static CategoriaDto paraDto(Categoria domain) {
        return CategoriaDto.builder()
                .id(domain.getId())
                .titulo(domain.getTitulo())
                .descricao(domain.getDescricao())
                .usuario(UsuarioMapperEntry.paraDto(domain.getUsuario()))
                .build();
    }

    public static Categoria paraDomain(CategoriaDto dto) {
        return Categoria.builder()
                .id(dto.getId())
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .usuario(UsuarioMapperEntry.paraDomain(dto.getUsuario()))
                .build();
    }
}
