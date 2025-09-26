package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.builder.UsuarioBuilder;
import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.entrypoint.dto.UsuarioDto;
import com.whatsapp.financeiro.validators.UsuarioValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioMapperEntryTest {

    private Usuario usuarioDomain;
    private UsuarioDto usuarioDto;

    @BeforeEach
    void setUp() {
        usuarioDomain = UsuarioBuilder.criarUsuarioDomain();
        usuarioDto = UsuarioBuilder.criarUsuarioDto();
    }

    @Test
    void deveMapearUsuarioDomainParaDto() {

        UsuarioDto resultado = UsuarioMapperEntry.paraDto(usuarioDomain);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(usuarioDto.getId(), resultado.getId());
        UsuarioValidator.validarUsuarioDto(usuarioDto, resultado);
    }

    @Test
    void deveMapearUsuarioDtoParaDomain() {

        Usuario resultado = UsuarioMapperEntry.paraDomain(usuarioDto);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(usuarioDomain.getId(), resultado.getId());
        UsuarioValidator.validarUsuarioDomain(usuarioDomain, resultado);
    }
}