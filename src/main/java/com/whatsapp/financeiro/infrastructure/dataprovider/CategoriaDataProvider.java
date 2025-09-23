package com.whatsapp.financeiro.infrastructure.dataprovider;

import com.whatsapp.financeiro.application.gateway.CategoriaGateway;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.infrastructure.exceptions.DataProviderException;
import com.whatsapp.financeiro.infrastructure.mapper.CategoriaMapperInfra;
import com.whatsapp.financeiro.infrastructure.repository.CategoriaRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoriaDataProvider implements CategoriaGateway {

    private final CategoriaRepository repository;

    public static final String MENSAGEM_ERRO_SALVAR_CATEGORIA = "Erro ao salvar a categoria.";
    public static final String MENSAGEM_ERRO_BUSCAR_CATEGORIAS = "Erro ao buscar todas as categorias.";
    public static final String MENSAGEM_ERRO_BUSCAR_CATEGORIA_POR_ID = "Erro ao buscar categoria por id";
    public static final String MENSAGEM_ERRO_DELETAR_CATEGORIA = "Erro ao deletar categoria";


    @Override
    public Categoria salvar(Categoria categoriaCriada) {
        CategoriaEntity categoriaEntity = CategoriaMapperInfra.paraEntity(categoriaCriada);

        try {
            categoriaEntity = repository.save(categoriaEntity);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_SALVAR_CATEGORIA, e);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_CATEGORIA, e);
        }

        return CategoriaMapperInfra.paraDomain(categoriaEntity);
    }

    @Override
    public Page<Categoria> consultarTodas(Pageable pageable) {
        Page<CategoriaEntity> categoriaPage;

        try {
            categoriaPage = repository.findAll(pageable);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_BUSCAR_CATEGORIAS, e);
            throw new DataProviderException(MENSAGEM_ERRO_BUSCAR_CATEGORIAS, e);
        }

        return categoriaPage.map(CategoriaMapperInfra::paraDomain);
    }

    @Override
    public Optional<Categoria> consultarPorId(UUID id) {
        Optional<CategoriaEntity> categoriaBuscada;

        try {
            categoriaBuscada = repository.findById(id);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_BUSCAR_CATEGORIA_POR_ID, e);
            throw new DataProviderException(MENSAGEM_ERRO_BUSCAR_CATEGORIA_POR_ID, e);
        }

        return categoriaBuscada.map(CategoriaMapperInfra::paraDomain);
    }

    @Override
    public void deletar(UUID id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_DELETAR_CATEGORIA, e);
            throw new DataProviderException(MENSAGEM_ERRO_DELETAR_CATEGORIA, e);
        }
    }
}
