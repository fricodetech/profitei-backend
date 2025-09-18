package com.whatsapp.financeiro.infrastructure.mapper;

import com.whatsapp.financeiro.builder.CategoriaBuilder;
import com.whatsapp.financeiro.builder.ClienteBuilder;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import com.whatsapp.financeiro.validators.CategoriaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoriaMapperInfraTest {

    @Mock
    private ClienteMapperInfra clienteMapper;

    @InjectMocks
    private CategoriaMapperInfra mapper;

    private Categoria domainTest;
    private CategoriaEntity entityTest;

    @BeforeEach
    void inicializar() {
        domainTest = CategoriaBuilder.criarCategoria();
        entityTest = CategoriaBuilder.criarCategoriaEntity();
    }

    @Test
    void deveMapearCategoriaDomainParaEntity() {
        Mockito.when(ClienteMapperInfra.paraEntity(Mockito.any())).thenReturn(ClienteBuilder.criarClienteEntity());

        CategoriaEntity categoriaTest = CategoriaMapperInfra.paraEntity(domainTest);

        CategoriaValidator.validaCategoriaEntity(categoriaTest, entityTest);
    }

    @Test
    void deveMapearCategoriaEntityParaDomain() {
        Mockito.when(ClienteMapperInfra.paraDomain(Mockito.any())).thenReturn(ClienteBuilder.criarCliente());

        Categoria categoriaTest = CategoriaMapperInfra.paraDomain(entityTest);

        CategoriaValidator.validaCategoriaDomain(categoriaTest, domainTest);
    }
}
