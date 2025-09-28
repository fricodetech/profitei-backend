package com.whatsapp.financeiro.infrastructure.dataprovider;

import com.whatsapp.financeiro.application.gateway.OperacaoGateway;
import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.domain.TipoOperacao;
import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.infrastructure.exceptions.DataProviderException;
import com.whatsapp.financeiro.infrastructure.mapper.OperacaoMapperInfra;
import com.whatsapp.financeiro.infrastructure.repository.OperacaoRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.OperacaoEntity;
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
public class OperacaoDataProvider implements OperacaoGateway {

    private final OperacaoRepository repository;

    public static final String MENSAGEM_ERRO_SALVAR_OPERACAO = "Erro ao salvar operacão.";
    public static final String MENSAGEM_ERRO_BUSCAR_OPERACOES = "Erro ao buscar todas os operacões.";
    public static final String MENSAGEM_ERRO_BUSCAR_OPERACAO_POR_ID = "Erro ao buscar operacão por id.";
    public static final String MENSAGEM_ERRO_DELETAR_OPERACAO = "Erro ao deletar operacão.";


    @Override
    public Operacao salvar(Operacao operacaoCriada) {
        OperacaoEntity operacaoEntity = OperacaoMapperInfra.paraEntity(operacaoCriada);

        try {
            operacaoEntity = repository.save(operacaoEntity);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_SALVAR_OPERACAO, e);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_OPERACAO, e);
        }

        return OperacaoMapperInfra.paraDomain(operacaoEntity);
    }

    @Override
    public Page<Operacao> consultarTodos(UUID idUsuario, Pageable pageable) {
        Page<OperacaoEntity> operacaoPage;

        try {
            operacaoPage = repository.findAllByUsuarioId(idUsuario, pageable);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_BUSCAR_OPERACOES, e);
            throw new DataProviderException(MENSAGEM_ERRO_BUSCAR_OPERACOES, e);
        }

        return operacaoPage.map(OperacaoMapperInfra::paraDomain);
    }

    @Override
    public Page<Operacao> consultarTodosPorTipoOperacao(TipoOperacao tipoOperacao, UUID idUsuario, Pageable pageable) {
        Page<OperacaoEntity> operacaoPage;

        try {
            operacaoPage = repository.findAllByTipoOperacaoAndUsuarioId(tipoOperacao, idUsuario, pageable);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_BUSCAR_OPERACOES, e);
            throw new DataProviderException(MENSAGEM_ERRO_BUSCAR_OPERACOES, e);
        }

        return operacaoPage.map(OperacaoMapperInfra::paraDomain);
    }

    @Override
    public Optional<Operacao> consultarPorId(UUID id) {
        Optional<OperacaoEntity> operacaoBuscada;

        try {
            operacaoBuscada = repository.findById(id);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_BUSCAR_OPERACAO_POR_ID, e);
            throw new DataProviderException(MENSAGEM_ERRO_BUSCAR_OPERACAO_POR_ID, e);
        }

        return operacaoBuscada.map(OperacaoMapperInfra::paraDomain);
    }

    @Override
    public void deletar(UUID id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            log.error(MENSAGEM_ERRO_DELETAR_OPERACAO, e);
            throw new DataProviderException(MENSAGEM_ERRO_DELETAR_OPERACAO, e);
        }
    }
}
