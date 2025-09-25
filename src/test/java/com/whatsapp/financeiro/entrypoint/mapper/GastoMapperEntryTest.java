package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.builder.GastoBuilder;
import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.entrypoint.dto.OperacaoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GastoMapperEntryTest {

    private Operacao gastoDomain;
    private OperacaoDto gastoDto;

    @BeforeEach
    void setUp() {
        gastoDomain = GastoBuilder.criarGastoDomain();
        gastoDto = GastoBuilder.criarGastoDto();
    }

    @Test
    void deveRetornarDtoComSucesso() {
        OperacaoDto resultado = OperacaoMapperEntry.paraDto(gastoDomain);
        Assertions.assertEquals(resultado.getId(), gastoDomain.getId());
        Assertions.assertEquals(resultado.getValor(), gastoDomain.getValor());
        Assertions.assertEquals(resultado.getDataGasto(), gastoDomain.getDataGasto());
        Assertions.assertEquals(resultado.getCategoria(), gastoDomain.getCategoria());
        Assertions.assertEquals(resultado.getUsuario(), gastoDomain.getUsuario());
    }

    @Test
    void deveRetornarDomainComSucesso() {
        Operacao resultado = OperacaoMapperEntry.paraDomain(gastoDto);
        Assertions.assertEquals(resultado.getId(), gastoDto.getId());
        Assertions.assertEquals(resultado.getValor(), gastoDto.getValor());
        Assertions.assertEquals(resultado.getDataGasto(), gastoDto.getDataGasto());
        Assertions.assertEquals(resultado.getCategoria(), gastoDto.getCategoria());
        Assertions.assertEquals(resultado.getUsuario(), gastoDto.getUsuario());
    }
}
