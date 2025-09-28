package com.whatsapp.financeiro.validators;

import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.entrypoint.dto.OperacaoDto;
import com.whatsapp.financeiro.infrastructure.repository.entities.OperacaoEntity;
import org.junit.jupiter.api.Assertions;

public class OperacaoValidator {

    public static void validarOperacaoDto(OperacaoDto esperado, OperacaoDto resultado) {
        Assertions.assertEquals(esperado.getDescricao(), resultado.getDescricao());
        Assertions.assertEquals(esperado.getTipoOperacao(), resultado.getTipoOperacao());
        Assertions.assertEquals(esperado.getValor(), resultado.getValor());
        Assertions.assertEquals(esperado.getDataOperacao(), resultado.getDataOperacao());
        CategoriaValidator.validarCategoriaDto(esperado.getCategoria(), resultado.getCategoria());
        UsuarioValidator.validarUsuarioDto(esperado.getUsuario(), resultado.getUsuario());
    }

    public static void validarOperacaoDomain(Operacao esperado, Operacao resultado) {
        Assertions.assertEquals(esperado.getDescricao(), resultado.getDescricao());
        Assertions.assertEquals(esperado.getTipoOperacao(), resultado.getTipoOperacao());
        Assertions.assertEquals(esperado.getValor(), resultado.getValor());
        Assertions.assertEquals(esperado.getDataOperacao(), resultado.getDataOperacao());
        CategoriaValidator.validarCategoriaDomain(esperado.getCategoria(), resultado.getCategoria());
        UsuarioValidator.validarUsuarioDomain(esperado.getUsuario(), resultado.getUsuario());
    }

    public static void validarOperacaoEntity(OperacaoEntity esperado, OperacaoEntity resultado) {
        Assertions.assertEquals(esperado.getDescricao(), resultado.getDescricao());
        Assertions.assertEquals(esperado.getTipoOperacao(), resultado.getTipoOperacao());
        Assertions.assertEquals(esperado.getValor(), resultado.getValor());
        Assertions.assertEquals(esperado.getDataOperacao(), resultado.getDataOperacao());
        CategoriaValidator.validarCategoriaEntity(esperado.getCategoria(), resultado.getCategoria());
        UsuarioValidator.validarUsuarioEntity(esperado.getUsuario(), resultado.getUsuario());
    }
}
