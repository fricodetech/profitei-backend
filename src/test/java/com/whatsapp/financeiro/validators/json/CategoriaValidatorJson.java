package com.whatsapp.financeiro.validators.json;

import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.entrypoint.dtos.CategoriaDto;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class CategoriaValidatorJson {
    public static void validaCategoriaJson(ResultActions resultado, CategoriaDto esperado) throws Exception {
        resultado.andExpect(jsonPath("$.dado.id").value(esperado.getId().toString()))
                .andExpect(jsonPath("$.dado.titulo").value(esperado.getTitulo()))
                .andExpect(jsonPath("$.dado.descricao").value(esperado.getDescricao()))

                .andExpect(jsonPath("$.dado.cliente.id").value(esperado.getCliente().getId().toString()))
                .andExpect(jsonPath("$.dado.cliente.nome").value(esperado.getCliente().getNome()))
                .andExpect(jsonPath("$.dado.cliente.email").value(esperado.getCliente().getEmail()))
                .andExpect(jsonPath("$.dado.cliente.telefone").value(esperado.getCliente().getTelefone()))
                .andExpect(jsonPath("$.dado.cliente.senha").value(esperado.getCliente().getSenha()))
                .andExpect(jsonPath("$.dado.cliente.dataCriacao").value(esperado.getCliente().getDataCriacao().toString()))

                .andExpect(jsonPath("$.dado.cliente.plano.id").value(esperado.getCliente().getPlano().getId().toString()))
                .andExpect(jsonPath("$.dado.cliente.plano.statusPlano").value(esperado.getCliente().getPlano().getStatusPlano().toString()))
                .andExpect(jsonPath("$.dado.cliente.plano.tipoPlano").value(esperado.getCliente().getPlano().getTipoPlano().toString()))
                .andExpect(jsonPath("$.dado.cliente.plano.valor").value(esperado.getCliente().getPlano().getValor().toString()))
                .andExpect(jsonPath("$.erro").doesNotExist());
    }

    public static void validaPageResponseCategoria(ResultActions resultado, int totalElementsEsperados) throws Exception {
        resultado.andExpect(jsonPath("$.dado.totalElements").value(totalElementsEsperados))
                .andExpect(jsonPath("$.dado.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.dado.pageable.pageSize").value(10))
                .andExpect(jsonPath("$.dado.first").value(true))
                .andExpect(jsonPath("$.dado.last").value(true))
                .andExpect(jsonPath("$.dado.empty").value(false))
                .andExpect(jsonPath("$.erro").doesNotExist());

        for (int i = 0; i < totalElementsEsperados; i++) {
            resultado.andExpect(jsonPath("$.dado.content[" + i + "].id").exists())
                    .andExpect(jsonPath("$.dado.content[" + i + "].titulo").isNotEmpty())
                    .andExpect(jsonPath("$.dado.content[" + i + "].descricao").isNotEmpty())

                    .andExpect(jsonPath("$.dado.content[" + i + "].cliente.id").exists())
                    .andExpect(jsonPath("$.dado.content[" + i + "].cliente.nome").isNotEmpty())
                    .andExpect(jsonPath("$.dado.content[" + i + "].cliente.email").isNotEmpty())
                    .andExpect(jsonPath("$.dado.content[" + i + "].cliente.telefone").isNotEmpty())
                    .andExpect(jsonPath("$.dado.content[" + i + "].cliente.senha").isNotEmpty())
                    .andExpect(jsonPath("$.dado.content[" + i + "].cliente.dataCriacao").isNotEmpty())

                    .andExpect(jsonPath("$.dado.content[" + i + "].cliente.plano.id").exists())
                    .andExpect(jsonPath("$.dado.content[" + i + "].cliente.plano.statusPlano").isNotEmpty())
                    .andExpect(jsonPath("$.dado.content[" + i + "].cliente.plano.tipoPlano").isNotEmpty())
                    .andExpect(jsonPath("$.dado.content[" + i + "].cliente.plano.valor").isNotEmpty());
        }
    }
}
