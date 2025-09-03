package com.whatsapp.financeiro.validators;

import com.whatsapp.financeiro.domain.Categoria;
import org.junit.jupiter.api.Assertions;

public class CategoriaValidator {
    public static void validaCategoriaDomain(Categoria esperado, Categoria resultado) {
        Assertions.assertEquals(esperado.getTitulo(), resultado.getTitulo());
        Assertions.assertEquals(esperado.getDescricao(), resultado.getDescricao());
        ClienteValidator.validaClienteDomain(esperado.getCliente(), resultado.getCliente());
    }
}
