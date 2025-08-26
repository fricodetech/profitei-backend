package com.whatsapp.financeiro.infrastructure.mappers;

import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.infrastructure.repositories.entities.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = ClienteMapperInfra.class
)
public interface CategoriaMapperInfra {
    CategoriaEntity paraEntity(Categoria domain);
    Categoria paraDomain(CategoriaEntity entity);
}
