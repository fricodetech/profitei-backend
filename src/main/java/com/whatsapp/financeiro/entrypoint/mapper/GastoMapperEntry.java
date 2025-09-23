package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.domain.Gasto;
import com.whatsapp.financeiro.entrypoint.dto.GastoDto;

public class GastoMapperEntry {
    public static GastoDto paraDto(Gasto domain) {
        return GastoDto.builder()
                .id(domain.getId())
                .valor(domain.getValor())
                .dataGasto(domain.getDataGasto())
                .categoria(CategoriaMapperEntry.paraDto(domain.getCategoria()))
                .usuario(UsuarioMapperEntry.paraDto(domain.getUsuario()))
                .build();
    }

    public static Gasto paraDomain(GastoDto dto) {
        return Gasto.builder()
                .id(dto.getId())
                .valor(dto.getValor())
                .dataGasto(dto.getDataGasto())
                .categoria(CategoriaMapperEntry.paraDomain(dto.getCategoria()))
                .usuario(UsuarioMapperEntry.paraDomain(dto.getUsuario()))
                .build();
    }
}
