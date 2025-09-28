package com.whatsapp.financeiro.domain;

import com.whatsapp.financeiro.builder.CategoriaBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoriaTest {

    @Test
    void deveAlterarDadosComSucesso() {
        Categoria categoria = CategoriaBuilder.criarCategoriaDomain();

        Categoria novosDados = categoria;
        novosDados.setTitulo("novo titulo");
        novosDados.setDescricao("nova descricao");

        categoria.alterarDados(novosDados);

        Assertions.assertEquals(novosDados.getTitulo(), categoria.getTitulo());
        Assertions.assertEquals(novosDados.getDescricao(), categoria.getDescricao());
    }
}
