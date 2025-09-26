package com.whatsapp.financeiro.domain;

import com.whatsapp.financeiro.builder.UsuarioBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = UsuarioBuilder.criarUsuarioDomain();
    }

    @Test
    void deveAlterarDadosComSucesso() {
        Usuario novosDados = Usuario.builder()
                .id(UUID.fromString("8d6cfaae-61da-49ef-935c-3419c0e00faf"))
                .nome("Nome teste 2")
                .email("emailteste2@gmail.com")
                .telefone("550000000001")
                .senha("senhha32187teste#")
                .build();

        usuario.setDados(novosDados);

        Assertions.assertNotEquals(usuario.getId(), novosDados.getId());
        Assertions.assertEquals(usuario.getNome(), novosDados.getNome());
        Assertions.assertEquals(usuario.getEmail(), novosDados.getEmail());
        Assertions.assertEquals(usuario.getTelefone(), novosDados.getTelefone());
        Assertions.assertNotEquals(usuario.getSenha(), novosDados.getSenha());
    }
}