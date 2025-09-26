package com.whatsapp.financeiro.infrastructure.mapper;

import com.whatsapp.financeiro.builder.UsuarioBuilder;
import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.infrastructure.repository.entities.UsuarioEntity;
import com.whatsapp.financeiro.validators.UsuarioValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioMapperInfraTest {

    private Usuario usuarioDomain;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        usuarioDomain = UsuarioBuilder.criarUsuarioDomain();
        usuarioEntity = UsuarioBuilder.criarUsuarioEntity();
    }

    @Test
    void deveMapearUsuarioDomainParaEntity() {
        UsuarioEntity resultado = UsuarioMapperInfra.paraEntity(usuarioDomain);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(usuarioEntity.getId(), resultado.getId());
        UsuarioValidator.validarUsuarioEntity(usuarioEntity, resultado);
    }

    @Test
    void deveMapearUsuarioDtoParaDomain() {
        Usuario resultado = UsuarioMapperInfra.paraDomain(usuarioEntity);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(usuarioDomain.getId(), resultado.getId());
        UsuarioValidator.validarUsuarioDomain(usuarioDomain, resultado);
    }
}