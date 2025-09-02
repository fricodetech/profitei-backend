package com.whatsapp.financeiro.builders;

import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.infrastructure.repositories.entities.CategoriaEntity;

import java.util.UUID;

public class CategoriaBuilder {

    public static Categoria criarCategoria() {
        return Categoria.builder()
                .id(UUID.fromString("41ad1798-be2a-4a35-9537-e355e80a5737"))
                .titulo("titulo bom")
                .descricao("descricao boa sim bao")
                .cliente(ClienteBuilder.criarCliente())
                .build();
    }

    public static CategoriaEntity criarCategoriaEntity() {
        return CategoriaEntity.builder()
                .id(UUID.fromString("41ad1798-be2a-4a35-9537-e355e80a5737"))
                .titulo("titulo bom")
                .descricao("descricao boa sim bão")
                .cliente(ClienteBuilder.criarClienteEntity())
                .build();
    }
}
