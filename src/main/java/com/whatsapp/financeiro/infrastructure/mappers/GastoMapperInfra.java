package com.whatsapp.financeiro.infrastructure.mappers;

import com.whatsapp.financeiro.domain.Gasto;
import com.whatsapp.financeiro.infrastructure.repositories.entities.GastoEntity;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = ClienteMapperInfra.class, CategoriaMapperInfra.class
)
public interface GastoMapperInfra {
    GastoEntity paraEntity(Gasto domain);
    Gasto paraDomain(GastoEntity entity);
}
