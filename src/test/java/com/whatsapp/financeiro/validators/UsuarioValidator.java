package com.whatsapp.financeiro.validators;

import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.entrypoint.dto.UsuarioDto;
import com.whatsapp.financeiro.infrastructure.repository.entities.UsuarioEntity;
import org.junit.jupiter.api.Assertions;

public class UsuarioValidator {
    public static void validarUsuarioDto(UsuarioDto esperado, UsuarioDto resultado) {
        Assertions.assertEquals(esperado.getNome(), resultado.getNome());
        Assertions.assertEquals(esperado.getEmail(), resultado.getEmail());
        Assertions.assertEquals(esperado.getTelefone(), resultado.getTelefone());
        Assertions.assertEquals(esperado.getSenha(), resultado.getTelefone());
        Assertions.assertEquals(esperado.getDataCriacao(), resultado.getDataCriacao());
        PlanoValidator.validarPlanoDto(esperado.getPlano(), resultado.getPlano());
    }

    public static void validarUsuarioDomain(Usuario esperado, Usuario resultado) {
        Assertions.assertEquals(esperado.getNome(), resultado.getNome());
        Assertions.assertEquals(esperado.getEmail(), resultado.getEmail());
        Assertions.assertEquals(esperado.getTelefone(), resultado.getTelefone());
        Assertions.assertEquals(esperado.getSenha(), resultado.getTelefone());
        Assertions.assertEquals(esperado.getDataCriacao(), resultado.getDataCriacao());
        PlanoValidator.validarPlanoDomain(esperado.getPlano(), resultado.getPlano());
    }

    public static void validarUsuarioEntity(UsuarioEntity esperado, UsuarioEntity resultado) {
        Assertions.assertEquals(esperado.getNome(), resultado.getNome());
        Assertions.assertEquals(esperado.getEmail(), resultado.getEmail());
        Assertions.assertEquals(esperado.getTelefone(), resultado.getTelefone());
        Assertions.assertEquals(esperado.getSenha(), resultado.getTelefone());
        Assertions.assertEquals(esperado.getDataCriacao(), resultado.getDataCriacao());
        PlanoValidator.validarPlanoEntity(esperado.getPlano(), resultado.getPlano());
    }
}
