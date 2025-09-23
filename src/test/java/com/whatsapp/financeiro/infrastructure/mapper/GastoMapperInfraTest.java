package com.whatsapp.financeiro.infrastructure.mapper;

import com.whatsapp.financeiro.builder.GastoBuilder;
import com.whatsapp.financeiro.domain.Gasto;
import com.whatsapp.financeiro.entrypoint.dto.GastoDto;
import com.whatsapp.financeiro.entrypoint.mapper.GastoMapperEntry;
import com.whatsapp.financeiro.infrastructure.repository.entities.GastoEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GastoMapperInfraTest {

    private Gasto gastoDomain;
    private GastoEntity gastoEntity;

    @BeforeEach
    void setUp() {
        gastoDomain = GastoBuilder.criarGastoDomain();
        gastoEntity = GastoBuilder.criarGastoEntity();
    }

    @Test
    void deveRetornarEntityComSucesso() {
        GastoEntity resultado = GastoMapperInfra.paraEntity(gastoDomain);
        Assertions.assertEquals(resultado.getId(), gastoDomain.getId());
        Assertions.assertEquals(resultado.getValor(), gastoDomain.getValor());
        Assertions.assertEquals(resultado.getDataGasto(), gastoDomain.getDataGasto());
        Assertions.assertEquals(resultado.getCategoria(), gastoDomain.getCategoria());
        Assertions.assertEquals(resultado.getUsuario(), gastoDomain.getUsuario());
    }

    @Test
    void deveRetornarDomainComSucesso() {
        Gasto resultado = GastoMapperInfra.paraDomain(gastoEntity);
        Assertions.assertEquals(resultado.getId(), gastoEntity.getId());
        Assertions.assertEquals(resultado.getValor(), gastoEntity.getValor());
        Assertions.assertEquals(resultado.getDataGasto(), gastoEntity.getDataGasto());
        Assertions.assertEquals(resultado.getCategoria(), gastoEntity.getCategoria());
        Assertions.assertEquals(resultado.getUsuario(), gastoEntity.getUsuario());
    }
}
