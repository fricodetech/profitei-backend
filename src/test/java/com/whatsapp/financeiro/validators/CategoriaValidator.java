package com.whatsapp.financeiro.validators;

import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.entrypoint.dto.CategoriaDto;
import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import org.junit.jupiter.api.Assertions;

public class CategoriaValidator {
    public static void validarCategoriaDto(CategoriaDto esperado, CategoriaDto resultado) {
        Assertions.assertEquals(esperado.getTitulo(), resultado.getTitulo());
        Assertions.assertEquals(esperado.getDescricao(), resultado.getDescricao());
        UsuarioValidator.validarUsuarioDto(esperado.getUsuario(), resultado.getUsuario());
    }

    public static void validarCategoriaDomain(Categoria esperado, Categoria resultado) {
        Assertions.assertEquals(esperado.getTitulo(), resultado.getTitulo());
        Assertions.assertEquals(esperado.getDescricao(), resultado.getDescricao());
        UsuarioValidator.validarUsuarioDomain(esperado.getUsuario(), resultado.getUsuario());
    }

    public static void validarCategoriaEntity(CategoriaEntity esperado, CategoriaEntity resultado) {
        Assertions.assertEquals(esperado.getTitulo(), resultado.getTitulo());
        Assertions.assertEquals(esperado.getDescricao(), resultado.getDescricao());
        UsuarioValidator.validarUsuarioEntity(esperado.getUsuario(), resultado.getUsuario());
    }
}
