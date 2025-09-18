package com.whatsapp.financeiro.infraestructure.mapper;

import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.infraestructure.repository.entities.UsuarioEntity;

public class UsuarioMapperInfra {
    public static UsuarioEntity paraEntity(Usuario domain) {
        return UsuarioEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .email(domain.getEmail())
                .telefone(domain.getTelefone())
                .senha(domain.getSenha())
                .plano(PlanoMapperInfra.paraEntity(domain.getPlano()))
                .build();
    }


    public static Usuario paraDomain(UsuarioEntity entity) {
        return Usuario.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .senha(entity.getSenha())
                .plano(PlanoMapperInfra.paraDomain(entity.getPlano()))
                .build();
    }
}
