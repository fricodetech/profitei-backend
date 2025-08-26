package com.whatsapp.financeiro.application.services;

import com.whatsapp.financeiro.application.gateways.CategoriaGateway;
import com.whatsapp.financeiro.domain.Categoria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaGateway gateway;

    public Categoria criarCategoria(Categoria categoriaCriada) {
        Categoria categoriaSalva = gateway.salvar(categoriaCriada);

        return categoriaSalva;
    }

    public List<Categoria> listarTodasCategorias() {
        List<Categoria> categoriaList = gateway.listarTodas();

        return categoriaList;
    }
}
