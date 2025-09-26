package com.whatsapp.financeiro.infrastructure.mapper;

import com.whatsapp.financeiro.builder.CategoriaBuilder;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import com.whatsapp.financeiro.validators.CategoriaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoriaMapperInfraTest {

    private Categoria domainTest;
    private CategoriaEntity entityTest;

    @BeforeEach
    void inicializar() {
        domainTest = CategoriaBuilder.criarCategoriaDomain();
        entityTest = CategoriaBuilder.criarCategoriaEntity();
    }

    @Test
    void deveMapearCategoriaDomainParaEntity() {

        CategoriaEntity categoriaTest = CategoriaMapperInfra.paraEntity(domainTest);

        Assertions.assertNotNull(categoriaTest);
        Assertions.assertEquals(entityTest.getId(), categoriaTest.getId());
        CategoriaValidator.validarCategoriaEntity(entityTest, categoriaTest);
    }

    @Test
    void deveMapearCategoriaEntityParaDomain() {

        Categoria categoriaTest = CategoriaMapperInfra.paraDomain(entityTest);

        Assertions.assertNotNull(categoriaTest);
        Assertions.assertEquals(domainTest.getId(), categoriaTest.getId());
        CategoriaValidator.validarCategoriaDomain(domainTest, categoriaTest);
    }
}
