package com.whatsapp.financeiro.infrastructure.dataproviders;

import com.whatsapp.financeiro.builders.CategoriaBuilder;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.infrastructure.mappers.CategoriaMapperInfra;
import com.whatsapp.financeiro.infrastructure.repositories.CategoriaRepository;
import com.whatsapp.financeiro.infrastructure.repositories.entities.CategoriaEntity;
import com.whatsapp.financeiro.infrastructure.validators.CategoriaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoriaDataProviderTest {

    @Mock
    private CategoriaRepository repository;

    @Mock
    private CategoriaMapperInfra mapper;

    @InjectMocks
    private CategoriaDataProvider dataProvider;

    private Categoria categoriaDomainTeste;
    private CategoriaEntity categoriaEntityTeste;

    @BeforeEach
    void inicializar() {
        categoriaDomainTeste = CategoriaBuilder.criarCategoria();
        categoriaEntityTeste = CategoriaBuilder.criarCategoriaEntity();
    }

    @Test
    void deveSalvarCategoriaComSucesso() {
        categoriaDomainTeste.setId(null);

        Mockito.when(repository.save(Mockito.any())).thenReturn(categoriaEntityTeste);
        Mockito.when(mapper.paraDomain(Mockito.any())).thenReturn(categoriaDomainTeste);
        Mockito.when(mapper.paraEntity(Mockito.any())).thenReturn(categoriaEntityTeste);

        Categoria categoriaResultado = dataProvider.salvar(categoriaDomainTeste);

        Assertions.assertNotNull(categoriaResultado.getId());
        CategoriaValidator.validaCategoriaDomain(categoriaDomainTeste, categoriaResultado);
    }
}
