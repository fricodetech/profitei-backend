package com.whatsapp.financeiro.builder;

import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.entrypoint.dto.CategoriaDto;
import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

public class CategoriaBuilder {

    public static CategoriaDto criarCategoriaDto() {
        return CategoriaDto.builder()
                .id(UUID.fromString("41ad1798-be2a-4a35-9537-e355e80a5737"))
                .titulo("titulo bom")
                .descricao("descricao boa sim bao")
                .cliente(ClienteBuilder.criarClienteDto())
                .build();
    }

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

    public static Page<Categoria> criarPageDeCategoria() {
        return new PageImpl<>(List.of(
                criarCategoria(),
                Categoria.builder()
                        .id(UUID.fromString("51ad1798-ce2a-5a35-0537-f355e80a5737"))
                        .titulo("outro titulo")
                        .descricao("outra boa descricao")
                        .cliente(ClienteBuilder.criarCliente())
                        .build()
        ));
    }

    public static Page<CategoriaEntity> criarPageDeCategoriaEntity() {
        return new PageImpl<>(List.of(
                criarCategoriaEntity(),
                CategoriaEntity.builder()
                        .id(UUID.fromString("51ad1798-ce2a-5a35-0537-f355e80a5737"))
                        .titulo("outro titulo")
                        .descricao("outra boa descricao")
                        .cliente(ClienteBuilder.criarClienteEntity())
                        .build()
        ));
    }


}
