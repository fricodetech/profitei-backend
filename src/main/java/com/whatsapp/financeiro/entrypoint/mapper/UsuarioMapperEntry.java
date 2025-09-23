package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.entrypoint.dto.UsuarioDto;

public class UsuarioMapperEntry {

    public static UsuarioDto paraDto(Usuario domain) {
        return UsuarioDto.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .email(domain.getEmail())
                .telefone(domain.getTelefone())
                .senha(domain.getSenha())
                .dataCriacao(domain.getDataCriacao())
                .plano(PlanoMapperEntry.paraDto(domain.getPlano()))
                .build();
    }

    public static Usuario paraDomain(UsuarioDto dto) {
        return Usuario.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .senha(dto.getSenha())
                .dataCriacao(dto.getDataCriacao())
                .plano(PlanoMapperEntry.paraDomain(dto.getPlano()))
                .build();
    }
}
