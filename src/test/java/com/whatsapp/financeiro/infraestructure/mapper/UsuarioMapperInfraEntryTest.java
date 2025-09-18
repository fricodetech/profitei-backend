package com.whatsapp.financeiro.infraestructure.mapper;

import com.whatsapp.financeiro.builder.UsuarioBuilder;
import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.infraestructure.repository.entities.UsuarioEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioMapperInfraEntryTest {

    private Usuario usuarioDomain;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        usuarioDomain = UsuarioBuilder.builderUsuarioDomain();
        usuarioEntity = UsuarioBuilder.builderUsuarioEntity();
    }

    @Test
    void deveRetornarEntityComSucesso() {
        UsuarioEntity resultado = UsuarioMapperInfra.paraEntity(usuarioDomain);
        Assertions.assertEquals(resultado.getId(), usuarioDomain.getId());
        Assertions.assertEquals(resultado.getNome(), usuarioDomain.getNome());
        Assertions.assertEquals(resultado.getEmail(), usuarioDomain.getEmail());
        Assertions.assertEquals(resultado.getTelefone(), usuarioDomain.getTelefone());
        Assertions.assertEquals(resultado.getSenha(), usuarioDomain.getSenha());
    }

    @Test
    void deveRetornarDomainComSucesso() {
        Usuario resultado = UsuarioMapperInfra.paraDomain(usuarioEntity);
        Assertions.assertEquals(resultado.getId(), usuarioEntity.getId());
        Assertions.assertEquals(resultado.getNome(), usuarioEntity.getNome());
        Assertions.assertEquals(resultado.getEmail(), usuarioEntity.getEmail());
        Assertions.assertEquals(resultado.getTelefone(), usuarioEntity.getTelefone());
        Assertions.assertEquals(resultado.getSenha(), usuarioEntity.getSenha());
    }
}