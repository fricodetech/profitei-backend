package com.whatsapp.financeiro.application.gateways;

import com.whatsapp.financeiro.domain.Categoria;

public interface CategoriaGateway {
    Categoria salvarCategoria(Categoria categoriaCriada);
}
