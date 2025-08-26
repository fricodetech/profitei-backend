package com.whatsapp.financeiro.application.gateways;

import com.whatsapp.financeiro.domain.Categoria;

import java.util.List;

public interface CategoriaGateway {
    Categoria salvar(Categoria categoriaCriada);
    List<Categoria> listarTodas();
}
