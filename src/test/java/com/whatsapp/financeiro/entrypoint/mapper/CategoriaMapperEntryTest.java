package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.builder.CategoriaBuilder;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.entrypoint.dto.CategoriaDto;

import com.whatsapp.financeiro.validators.CategoriaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoriaMapperEntryTest {

    private Categoria domainTest;
    private CategoriaDto dtoTest;

    @BeforeEach
    void inicializar() {
        domainTest = CategoriaBuilder.criarCategoriaDomain();
        dtoTest = CategoriaBuilder.criarCategoriaDto();
    }

    @Test
    void deveMapearCategoriaDomainParaDto() {

        CategoriaDto categoriaTest = CategoriaMapperEntry.paraDto(domainTest);

        Assertions.assertNotNull(categoriaTest);
        Assertions.assertEquals(dtoTest.getId(), categoriaTest.getId());
        CategoriaValidator.validarCategoriaDto(dtoTest, categoriaTest);
    }

    @Test
    void deveMapearCategoriaDtoParaDomain() {

        Categoria categoriaTest = CategoriaMapperEntry.paraDomain(dtoTest);

        Assertions.assertNotNull(categoriaTest);
        Assertions.assertEquals(domainTest.getId(), categoriaTest.getId());
        CategoriaValidator.validarCategoriaDomain(domainTest, categoriaTest);
    }
}
