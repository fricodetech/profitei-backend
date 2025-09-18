package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.builder.CategoriaBuilder;
import com.whatsapp.financeiro.builder.ClienteBuilder;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.entrypoint.dto.CategoriaDto;

import com.whatsapp.financeiro.validators.CategoriaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoriaMapperEntryTest {

    private Categoria domainTest;
    private CategoriaDto dtoTest;

    @BeforeEach
    void inicializar() {
        domainTest = CategoriaBuilder.criarCategoria();
        dtoTest = CategoriaBuilder.criarCategoriaDto();
    }

    @Test
    void deveMapearCategoriaDomainParaDto() {
        Mockito.when(ClienteMapperEntry.paraDto(Mockito.any())).thenReturn(ClienteBuilder.criarClienteDto());

        CategoriaDto categoriaTest = CategoriaMapperEntry.paraDto(domainTest);

        CategoriaValidator.validaCategoriaDto(categoriaTest, dtoTest);
    }

    @Test
    void deveMapearCategoriaDtoParaDomain() {
        Mockito.when(ClienteMapperEntry.paraDomain(Mockito.any())).thenReturn(ClienteBuilder.criarCliente());

        Categoria categoriaTest = CategoriaMapperEntry.paraDomain(dtoTest);

        CategoriaValidator.validaCategoriaDomain(categoriaTest, domainTest);
    }
}
