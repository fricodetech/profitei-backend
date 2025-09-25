package com.whatsapp.financeiro.builder;

import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.entrypoint.dto.OperacaoDto;
import com.whatsapp.financeiro.infrastructure.repository.entities.OperacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class GastoBuilder {

    public static OperacaoDto criarGastoDto() {
        return OperacaoDto.builder()
                .id(UUID.fromString("41ad1798-be2a-4a35-9537-e355e80a5737"))
                .valor(BigDecimal.valueOf(10))
                .dataGasto(LocalDate.of(2012,12,12))
                .categoria(CategoriaBuilder.criarCategoriaDto())
                .usuario(UsuarioBuilder.builderUsuarioDto())
                .build();
    }

    public static Operacao criarGastoDomain() {
        return Operacao.builder()
                .id(UUID.fromString("41ad1798-be2a-4a35-9537-e355e80a5737"))
                .valor(BigDecimal.valueOf(10))
                .dataGasto(LocalDate.of(2012,12,12))
                .categoria(CategoriaBuilder.criarCategoria())
                .usuario(UsuarioBuilder.builderUsuarioDomain())
                .build();
    }

    public static OperacaoEntity criarGastoEntity() {
        return OperacaoEntity.builder()
                .id(UUID.fromString("41ad1798-be2a-4a35-9537-e355e80a5737"))
                .valor(BigDecimal.valueOf(10))
                .dataGasto(LocalDate.of(2012,12,12))
                .categoria(CategoriaBuilder.criarCategoriaEntity())
                .usuario(UsuarioBuilder.builderUsuarioEntity())
                .build();
    }

    public static Page<Operacao> criarPageDeGastoDomain() {
        return new PageImpl<>(List.of(
                criarGastoDomain(),
                Operacao.builder()
                        .id(UUID.fromString("51ad1798-ce2a-5a35-0537-f355e80a5737"))
                        .valor(BigDecimal.valueOf(11))
                        .dataGasto(LocalDate.of(2011,11,11))
                        .categoria(CategoriaBuilder.criarCategoria())
                        .usuario(UsuarioBuilder.builderUsuarioDomain())
                        .build()
        ));
    }
}
