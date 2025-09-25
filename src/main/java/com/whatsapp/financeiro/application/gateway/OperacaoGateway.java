package com.whatsapp.financeiro.application.gateway;

import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.domain.TipoOperacao;
import com.whatsapp.financeiro.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface OperacaoGateway {
    Operacao salvar(Operacao gastoCriado);
    Page<Operacao> consultarTodos(UUID idUsuario, Pageable pageable);
    Page<Operacao> consultarTodosPorTipoOperacao(TipoOperacao tipoOperacao, UUID idUsuario, Pageable pageable);
    Optional<Operacao> consultarPorId(UUID id);
    void deletar(UUID id);
}
