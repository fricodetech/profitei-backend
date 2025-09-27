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
                .usuario(UsuarioBuilder.criarUsuarioDto())
                .build();
    }

    public static Categoria criarCategoriaDomain() {
        return Categoria.builder()
                .id(UUID.fromString("41ad1798-be2a-4a35-9537-e355e80a5737"))
                .titulo("titulo bom")
                .descricao("descricao boa sim bao")
                .usuario(UsuarioBuilder.criarUsuarioDomain())
                .build();
    }

    public static CategoriaEntity criarCategoriaEntity() {
        return CategoriaEntity.builder()
                .id(UUID.fromString("41ad1798-be2a-4a35-9537-e355e80a5737"))
                .titulo("titulo bom")
                .descricao("descricao boa sim bão")
                .usuario(UsuarioBuilder.criarUsuarioEntity())
                .build();
    }

    public static Page<Categoria> criarPageDeCategoriaDomain() {
        return new PageImpl<>(List.of(
                criarCategoriaDomain(),
                criarCategoriaDomain()
        ));
    }

    public static Page<CategoriaEntity> criarPageDeCategoriaEntity() {
        return new PageImpl<>(List.of(
                criarCategoriaEntity(),
                criarCategoriaEntity()
        ));
    }
}
