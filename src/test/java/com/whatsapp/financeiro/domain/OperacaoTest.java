package com.whatsapp.financeiro.domain;

import com.whatsapp.financeiro.builder.CategoriaBuilder;
import com.whatsapp.financeiro.builder.OperacaoBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OperacaoTest {

    @Test
    void deveAlterarDadosComSucesso() {
        Operacao operacao = OperacaoBuilder.criarOperacaoDomain(TipoOperacao.GANHO);

        Categoria categoria = CategoriaBuilder.criarCategoriaDomain();
        categoria.setTitulo("Novo titulo");

        Operacao novosDados = operacao;
        novosDados.setDescricao("Nova descrição");
        novosDados.setValor(BigDecimal.valueOf(30));
        novosDados.setDataOperacao(LocalDate.now());
        novosDados.setCategoria(categoria);


        operacao.alterarDados(novosDados);

        Assertions.assertEquals(novosDados.getDescricao(), operacao.getDescricao());
        Assertions.assertEquals(novosDados.getValor(), operacao.getValor());
        Assertions.assertEquals(novosDados.getDataOperacao(), operacao.getDataOperacao());
        Assertions.assertEquals(novosDados.getCategoria().getTitulo(), operacao.getCategoria().getTitulo());
    }
}
