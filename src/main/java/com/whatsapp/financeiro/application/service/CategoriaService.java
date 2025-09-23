package com.whatsapp.financeiro.application.service;

import com.whatsapp.financeiro.application.exceptions.CategoriaNaoEncontradaException;
import com.whatsapp.financeiro.application.gateway.CategoriaGateway;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaGateway gateway;
    private final UsuarioService usuarioService;

    public static final String ERRO_CATEGORIA_NAO_ENCONTRADA = "Erro ao buscar por id";
    public static final String ERRO_ALTERAR_CAMPO = "Erro ao alterar campo: ";

    public Categoria criarCategoria(Categoria categoriaCriada) {
        log.info("Criando categoria. Categoria: {}", categoriaCriada.toString());

        Usuario usuarioBuscado = usuarioService.consultarPorId(categoriaCriada.getUsuario().getId());
        categoriaCriada.setUsuario(usuarioBuscado);

        Categoria categoriaSalva = gateway.salvar(categoriaCriada);

        log.info("Categoria criada com sucesso! Categoria: {}", categoriaSalva.toString());
        return categoriaSalva;
    }

    public Page<Categoria> consultarTodasCategorias(Pageable pageable) {
        log.info("Buscando todas as categorias salvas.");

        Page<Categoria> categoriaPage = gateway.consultarTodas(pageable);

        log.info("Categorias buscadas com sucesso! Categorias: {}", categoriaPage.getTotalElements());
        return categoriaPage;
    }

    public Categoria consultarCategoriaPorId(UUID id) {
        log.info("Buscando categoria por id. Id: {}", id);

        Optional<Categoria> categoriaOptional = gateway.consultarPorId(id);

        if (categoriaOptional.isEmpty()) {
            throw new CategoriaNaoEncontradaException(ERRO_CATEGORIA_NAO_ENCONTRADA);
        }

        log.info("Categoria buscada com sucesso! Categoria: {}", categoriaOptional.get());
        return categoriaOptional.get();
    }

    public Categoria alterarCategoria(UUID id, Categoria categoriaNova) {
        log.info("Alterando categoria por id. Id: {}, Categoria nova: {}", id, categoriaNova);

        Categoria categoriaBuscada = this.consultarCategoriaPorId(id);

        categoriaBuscada.alterarAtributos(categoriaNova);

        Categoria categoriaSalva = gateway.salvar(categoriaBuscada);

        log.info("Categoria alterada com sucesso! Categoria: {}", categoriaSalva.toString());
        return categoriaSalva;
    }

    public void deletarCategoria(UUID id) {
        log.info("Deletando categoria por id. Id: {}", id);

        consultarCategoriaPorId(id);
        gateway.deletar(id);

        log.info("Categoria deletada com sucesso!");
    }
}
