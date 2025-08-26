package com.whatsapp.financeiro.application.services;

import com.whatsapp.financeiro.application.gateways.CategoriaGateway;
import com.whatsapp.financeiro.domain.Categoria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaGateway gateway;

    public Categoria criarCategoria(Categoria categoriaCriada) {
        Categoria categoriaSalva = gateway.salvarCategoria(categoriaCriada);

        return categoriaSalva;
    }
}
