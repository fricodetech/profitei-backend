package com.whatsapp.financeiro.builder;

import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.entrypoint.dto.UsuarioDto;
import com.whatsapp.financeiro.infrastructure.repository.entities.UsuarioEntity;

import java.time.LocalDate;
import java.util.UUID;

public class UsuarioBuilder {

    public static UsuarioDto criarUsuarioDto() {
        return UsuarioDto.builder()
                .id(UUID.fromString("bfd2fb0f-255a-40a4-be4e-cfe311aabb14"))
                .nome("Nome teste")
                .email("emailteste@gmail.com")
                .telefone("559988774444")
                .senha("senhateste321#")
                .dataCriacao(LocalDate.of(2012, 12, 12))
                .plano(PlanoBuilder.criarPlanoDto())
                .build();
    }

    public static Usuario criarUsuarioDomain() {
        return Usuario.builder()
                .id(UUID.fromString("bfd2fb0f-255a-40a4-be4e-cfe311aabb14"))
                .nome("Nome teste")
                .email("emailteste@gmail.com")
                .telefone("559988774444")
                .senha("senhateste321#")
                .dataCriacao(LocalDate.of(2012, 12, 12))
                .plano(PlanoBuilder.criarPlanoDomain())
                .build();
    }

    public static UsuarioEntity criarUsuarioEntity() {
        return UsuarioEntity.builder()
                .id(UUID.fromString("bfd2fb0f-255a-40a4-be4e-cfe311aabb14"))
                .nome("Nome teste")
                .email("emailteste@gmail.com")
                .telefone("559988774444")
                .senha("senhateste321#")
                .dataCriacao(LocalDate.of(2012, 12, 12))
                .plano(PlanoBuilder.criarPlanoEntity())
                .build();
    }
}
