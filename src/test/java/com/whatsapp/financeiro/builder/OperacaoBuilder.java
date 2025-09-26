package com.whatsapp.financeiro.builder;

import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.domain.TipoOperacao;
import com.whatsapp.financeiro.entrypoint.dto.OperacaoDto;
import com.whatsapp.financeiro.infrastructure.repository.entities.OperacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class OperacaoBuilder {

    public static OperacaoDto criarOperacaoDto(TipoOperacao tipoOperacao) {
        return OperacaoDto.builder()
                .id(UUID.fromString("41ad1798-be2a-4a35-9537-e355e80a5737"))
                .descricao("descricao teste")
                .tipoOperacao(tipoOperacao)
                .valor(BigDecimal.valueOf(10))
                .dataOperacao(LocalDate.of(2012,12,12))
                .categoria(CategoriaBuilder.criarCategoriaDto())
                .usuario(UsuarioBuilder.criarUsuarioDto())
                .build();
    }

    public static Operacao criarOperacaoDomain(TipoOperacao tipoOperacao) {
        return Operacao.builder()
                .id(UUID.fromString("41ad1798-be2a-4a35-9537-e355e80a5737"))
                .descricao("descricao teste")
                .tipoOperacao(tipoOperacao)
                .valor(BigDecimal.valueOf(10))
                .dataOperacao(LocalDate.of(2012, 12, 12))
                .categoria(CategoriaBuilder.criarCategoriaDomain())
                .usuario(UsuarioBuilder.criarUsuarioDomain())
                .build();
    }

    public static OperacaoEntity criarOperacaoEntity(TipoOperacao tipoOperacao) {
        return OperacaoEntity.builder()
                .id(UUID.fromString("41ad1798-be2a-4a35-9537-e355e80a5737"))
                .descricao("descricao teste")
                .tipoOperacao(tipoOperacao)
                .valor(BigDecimal.valueOf(10))
                .dataOperacao(LocalDate.of(2012, 12, 12))
                .categoria(CategoriaBuilder.criarCategoriaEntity())
                .usuario(UsuarioBuilder.criarUsuarioEntity())
                .build();
    }

    public static Page<Operacao> criarPageDeGastoDomain() {
        return new PageImpl<>(List.of(
                criarOperacaoDomain(),
                Operacao.builder()
                        .id(UUID.fromString("51ad1798-ce2a-5a35-0537-f355e80a5737"))
                        .descricao("descricao teste")
                        .tipoOperacao(tipoOperacao)
                        .valor(BigDecimal.valueOf(10))
                        .dataOperacao(LocalDate.of(2012, 12, 12))
                        .categoria(CategoriaBuilder.criarCategoriaDomain())
                        .usuario(UsuarioBuilder.criarUsuarioDomain())
                        .build()
        ));
    }
}
