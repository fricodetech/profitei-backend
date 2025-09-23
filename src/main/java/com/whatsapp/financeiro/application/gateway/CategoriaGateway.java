package com.whatsapp.financeiro.application.gateway;

import com.whatsapp.financeiro.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CategoriaGateway {
    Categoria salvar(Categoria categoriaCriada);
    Page<Categoria> consultarTodas(Pageable pageable);
    Optional<Categoria> consultarPorId(UUID id);
    void deletar(UUID id);
}
