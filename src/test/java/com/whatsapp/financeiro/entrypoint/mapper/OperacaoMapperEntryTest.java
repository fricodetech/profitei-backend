package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.builder.OperacaoBuilder;
import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.domain.TipoOperacao;
import com.whatsapp.financeiro.entrypoint.dto.OperacaoDto;
import com.whatsapp.financeiro.validators.OperacaoValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperacaoMapperEntryTest {

    private Operacao operacaoDomain;
    private OperacaoDto operacaoDto;

    @BeforeEach
    void inicializar() {
        operacaoDomain = OperacaoBuilder.criarOperacaoDomain(TipoOperacao.GANHO);
        operacaoDto = OperacaoBuilder.criarOperacaoDto(TipoOperacao.GANHO);
    }

    @Test
    void deveMapearOperacaoDomainParaDto() {

        OperacaoDto resultado = OperacaoMapperEntry.paraDto(operacaoDomain);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(operacaoDto.getId(), resultado.getId());
        OperacaoValidator.validarOperacaoDto(operacaoDto, resultado);
    }

    @Test
    void deveMapearOperacaoDtoParaDomain() {
        Operacao resultado = OperacaoMapperEntry.paraDomain(operacaoDto);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(operacaoDomain.getId(), resultado.getId());
        OperacaoValidator.validarOperacaoDomain(operacaoDomain, resultado);
    }
}
