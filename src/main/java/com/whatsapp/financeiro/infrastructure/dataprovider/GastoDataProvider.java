package com.whatsapp.financeiro.infrastructure.dataprovider;

import com.whatsapp.financeiro.application.gateway.GastoGateway;
import com.whatsapp.financeiro.domain.Gasto;
import com.whatsapp.financeiro.infrastructure.exceptions.DataProviderException;
import com.whatsapp.financeiro.infrastructure.mapper.GastoMapperInfra;
import com.whatsapp.financeiro.infrastructure.repository.GastoRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.GastoEntity;
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
public class GastoDataProvider implements GastoGateway {

    private final GastoRepository repository;

    public static final String MENSAGEM_ERRO_SALVAR_GASTO = "Erro ao salvar o gasto.";
    public static final String MENSAGEM_ERRO_BUSCAR_GASTOS = "Erro ao buscar todas os gastos.";
    public static final String MENSAGEM_ERRO_BUSCAR_GASTO_POR_ID = "Erro ao buscar gasto por id";
    public static final String MENSAGEM_ERRO_DELETAR_GASTO = "Erro ao deletar gasto";


    @Override
    public Gasto salvar(Gasto gastoCriado) {
        GastoEntity gastoEntity = GastoMapperInfra.paraEntity(gastoCriado);

        try {
            gastoEntity = repository.save(gastoEntity);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_SALVAR_GASTO, e);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_GASTO, e);
        }

        return GastoMapperInfra.paraDomain(gastoEntity);
    }

    @Override
    public Page<Gasto> consultarTodos(Pageable pageable) {
        Page<GastoEntity> gastoPage;

        try {
            gastoPage = repository.findAll(pageable);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_BUSCAR_GASTOS, e);
            throw new DataProviderException(MENSAGEM_ERRO_BUSCAR_GASTOS, e);
        }

        return gastoPage.map(GastoMapperInfra::paraDomain);
    }

    @Override
    public Optional<Gasto> consultarPorId(UUID id) {
        Optional<GastoEntity> gastoBuscado;

        try {
            gastoBuscado = repository.findById(id);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_BUSCAR_GASTO_POR_ID, e);
            throw new DataProviderException(MENSAGEM_ERRO_BUSCAR_GASTO_POR_ID, e);
        }

        return gastoBuscado.map(GastoMapperInfra::paraDomain);
    }

    @Override
    public void deletar(UUID id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_DELETAR_GASTO, e);
            throw new DataProviderException(MENSAGEM_ERRO_DELETAR_GASTO, e);
        }
    }
}
