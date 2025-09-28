package com.whatsapp.financeiro.domain;

import com.whatsapp.financeiro.builder.PlanoBuilder;
import com.whatsapp.financeiro.builder.UsuarioBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class UsuarioTest {

    @Test
    void deveAlterarDadosComSucesso() {
        Usuario usuario = UsuarioBuilder.criarUsuarioDomain();

        Plano planoNovo = PlanoBuilder.criarPlanoDomain();
        planoNovo.setTipoPlano(TipoPlano.PREMIUM);
        planoNovo.setValor(BigDecimal.valueOf(30));

        Usuario novosDados = usuario;
        novosDados.setNome("Novo nome");
        novosDados.setEmail("Novo email");
        novosDados.setTelefone("44995500881");
        novosDados.setPlano(planoNovo);

        usuario.setDados(novosDados);

        Assertions.assertEquals(novosDados.getNome(), usuario.getNome());
        Assertions.assertEquals(novosDados.getEmail(), usuario.getEmail());
        Assertions.assertEquals(novosDados.getTelefone(), usuario.getTelefone());
        Assertions.assertEquals(novosDados.getPlano().getTipoPlano(), usuario.getPlano().getTipoPlano());
        Assertions.assertEquals(novosDados.getPlano().getValor(), usuario.getPlano().getValor());
    }
}