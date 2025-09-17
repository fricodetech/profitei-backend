package com.whatsapp.financeiro.validators;

import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.entrypoint.dto.CategoriaDto;
import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import org.junit.jupiter.api.Assertions;

public class CategoriaValidator {
    public static void validaCategoriaDto(CategoriaDto esperado, CategoriaDto resultado) {
        Assertions.assertEquals(esperado.getTitulo(), resultado.getTitulo());
        Assertions.assertEquals(esperado.getDescricao(), resultado.getDescricao());
        ClienteValidator.validaClienteDto(esperado.getCliente(), resultado.getCliente());
    }

    public static void validaCategoriaDomain(Categoria esperado, Categoria resultado) {
        Assertions.assertEquals(esperado.getTitulo(), resultado.getTitulo());
        Assertions.assertEquals(esperado.getDescricao(), resultado.getDescricao());
        ClienteValidator.validaClienteDomain(esperado.getCliente(), resultado.getCliente());
    }

    public static void validaCategoriaEntity(CategoriaEntity esperado, CategoriaEntity resultado) {
        Assertions.assertEquals(esperado.getTitulo(), resultado.getTitulo());
        Assertions.assertEquals(esperado.getDescricao(), resultado.getDescricao());
        ClienteValidator.validaClienteEntity(esperado.getCliente(), resultado.getCliente());
    }
}
