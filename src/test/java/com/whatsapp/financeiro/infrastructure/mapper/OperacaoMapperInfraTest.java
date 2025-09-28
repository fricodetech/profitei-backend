package com.whatsapp.financeiro.infrastructure.mapper;

import com.whatsapp.financeiro.builder.OperacaoBuilder;
import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.domain.TipoOperacao;
import com.whatsapp.financeiro.infrastructure.repository.entities.OperacaoEntity;
import com.whatsapp.financeiro.validators.OperacaoValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperacaoMapperInfraTest {

    private Operacao operacaoDomain;
    private OperacaoEntity operacaoEntity;

    @BeforeEach
    void inicializar() {
        operacaoDomain = OperacaoBuilder.criarOperacaoDomain(TipoOperacao.GANHO);
        operacaoEntity = OperacaoBuilder.criarOperacaoEntity(TipoOperacao.GANHO);
    }

    @Test
    void deveMapearOperacaoDomainParaEntity() {

        OperacaoEntity resultado = OperacaoMapperInfra.paraEntity(operacaoDomain);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(operacaoEntity.getId(), resultado.getId());
        OperacaoValidator.validarOperacaoEntity(operacaoEntity, resultado);
    }

    @Test
    void deveMapearOperacaoEntityParaDomain() {
        Operacao resultado = OperacaoMapperInfra.paraDomain(operacaoEntity);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(operacaoDomain.getId(), resultado.getId());
        OperacaoValidator.validarOperacaoDomain(operacaoDomain, resultado);
    }
}
