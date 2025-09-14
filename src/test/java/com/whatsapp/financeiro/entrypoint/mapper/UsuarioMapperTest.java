package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.builder.UsuarioBuilder;
import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.entrypoint.dto.UsuarioDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    private Usuario usuarioDomain;
    private UsuarioDto usuarioDto;

    @BeforeEach
    void setUp() {
        usuarioDomain = UsuarioBuilder.builderUsuarioDomain();
        usuarioDto = UsuarioBuilder.builderUsuarioDto();
    }

    @Test
    void deveRetornarDtoComSucesso() {
        UsuarioDto resultado = UsuarioMapper.paraDto(usuarioDomain);
        Assertions.assertEquals(resultado.getId(), usuarioDomain.getId());
        Assertions.assertEquals(resultado.getNome(), usuarioDomain.getNome());
        Assertions.assertEquals(resultado.getEmail(), usuarioDomain.getEmail());
        Assertions.assertEquals(resultado.getTelefone(), usuarioDomain.getTelefone());
        Assertions.assertEquals(resultado.getSenha(), usuarioDomain.getSenha());
    }

    @Test
    void deveRetornarDomainComSucesso() {
        Usuario resultado = UsuarioMapper.paraDomain(usuarioDto);
        Assertions.assertEquals(resultado.getId(), usuarioDto.getId());
        Assertions.assertEquals(resultado.getNome(), usuarioDto.getNome());
        Assertions.assertEquals(resultado.getEmail(), usuarioDto.getEmail());
        Assertions.assertEquals(resultado.getTelefone(), usuarioDto.getTelefone());
        Assertions.assertEquals(resultado.getSenha(), usuarioDto.getSenha());
    }
}