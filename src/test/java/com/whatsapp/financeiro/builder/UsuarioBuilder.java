package com.whatsapp.financeiro.builder;

import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.entrypoint.dto.UsuarioDto;
import com.whatsapp.financeiro.infraestructure.repository.entities.UsuarioEntity;

import java.util.UUID;

public class UsuarioBuilder {

    public static Usuario builderUsuarioDomain() {
        return Usuario.builder()
                .id(UUID.fromString("f11af252-fc20-4671-96f1-089234601b29"))
                .nome("Nome teste")
                .email("emailteste@gmail.com")
                .telefone("559988774455")
                .senha("senhateste123@")
                .build();
    }

    public static UsuarioDto builderUsuarioDto() {
        return UsuarioDto.builder()
                .id(UUID.fromString("bfd2fb0f-255a-40a4-be4e-cfe311aabb14"))
                .nome("Nome teste dto")
                .email("emailtestedto@gmail.com")
                .telefone("559988774444")
                .senha("senhateste321#")
                .build();
    }

    public static UsuarioEntity builderUsuarioEntity() {
        return UsuarioEntity.builder()
                .id(UUID.fromString("cd895262-73cc-4f4c-a9dd-78809ee8e9b9"))
                .nome("Nome teste ent")
                .email("emailtesteent@gmail.com")
                .telefone("559988774433")
                .senha("senhateste231$")
                .build();
    }
}
