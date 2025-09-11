package com.whatsapp.financeiro.application.gateways;

import com.whatsapp.financeiro.domain.Gasto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface GastoGateway {
    Gasto salvar(Gasto gastoCriado);
    Page<Gasto> buscarTodos(Pageable pageable);
    Optional<Gasto> buscarPorId(UUID id);
    void deletar(UUID id);
}
