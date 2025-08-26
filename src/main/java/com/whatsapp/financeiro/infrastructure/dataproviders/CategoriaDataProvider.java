package com.whatsapp.financeiro.infrastructure.dataproviders;

import com.whatsapp.financeiro.application.gateways.CategoriaGateway;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.infrastructure.exception.DataProviderException;
import com.whatsapp.financeiro.infrastructure.mappers.CategoriaMapperInfra;
import com.whatsapp.financeiro.infrastructure.repositories.CategoriaRepository;
import com.whatsapp.financeiro.infrastructure.repositories.entities.CategoriaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoriaDataProvider implements CategoriaGateway {

    private final CategoriaRepository repository;
    private final CategoriaMapperInfra mapper;

    public static final String MENSAGEM_ERRO_SALVAR_CATEGORIA = "Erro ao salvar a categoria.";

    @Override
    public Categoria salvarCategoria(Categoria categoriaCriada) {

        CategoriaEntity categoriaEntity = mapper.paraEntity(categoriaCriada);

        try {
            categoriaEntity = repository.save(categoriaEntity);
        } catch (Exception e) {
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_CATEGORIA, e);
        }

        return mapper.paraDomain(categoriaEntity);
    }
}
