package com.whatsapp.financeiro.entrypoint.mappers;

import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.entrypoint.dtos.CategoriaDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = ClienteMapperEntry.class
)
public interface CategoriaMapperEntry {
    CategoriaDto paraDto(Categoria domain);
    Categoria paraDomain(CategoriaDto dto);
}
