package com.whatsapp.financeiro.application.services;

import com.whatsapp.financeiro.application.exceptions.AlterarCamposException;
import com.whatsapp.financeiro.application.exceptions.CategoriaNaoEncontradaException;
import com.whatsapp.financeiro.application.gateways.CategoriaGateway;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.domain.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaGateway gateway;
    private final ClienteService clienteService;

    public Categoria criarCategoria(Categoria categoriaCriada) {
        log.info("Criando categoria. Categoria: {}", categoriaCriada.toString());

        Cliente clienteBuscado = clienteService.buscarClientePorId(categoriaCriada.getCliente().getId());
        categoriaCriada.setCliente(clienteBuscado);

        Categoria categoriaSalva = gateway.salvar(categoriaCriada);

        log.info("Categoria criado com sucesso! Categoria: {}", categoriaSalva.toString());
        return categoriaSalva;
    }

    public Page<Categoria> buscarTodasCategorias(Pageable pageable) {
        log.info("Buscando todas as categorias salvas.");

        Page<Categoria> categoriaPage = gateway.buscarTodas(pageable);

        log.info("Categorias buscadas com sucesso! Categorias: {}", categoriaPage.getTotalElements());
        return categoriaPage;
    }

    public Categoria buscarCategoriaPorId(UUID id) {
        log.info("Buscando categoria por id. Id: {}", id);

        Optional<Categoria> categoriaOptional = gateway.buscarPorId(id);

        if (categoriaOptional.isEmpty()) {
            throw new CategoriaNaoEncontradaException("Erro ao buscar por id");
        }

        log.info("Categoria buscada com sucesso! Categoria: {}", categoriaOptional.get());
        return categoriaOptional.get();
    }

    public Categoria alterarCategoria(UUID id, Map<String, Object> campos) {
        log.info("Alterando categoria por id. Id: {}, Campos: {}", id, campos);

        //Buscando categoria
        Categoria categoriaBuscada = this.buscarCategoriaPorId(id);

        //Alterando campos
        campos.forEach((nome, valor) -> {
            if(nome.equals("id") || nome.equals("cliente")) return;
            try {
                Field field = Categoria.class.getDeclaredField(nome);
                field.setAccessible(true);
                field.set(categoriaBuscada, valor);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("Erro ao alterar campo: {}", nome, e);
                throw new AlterarCamposException("Erro ao alterar campo: " + nome, e);
            }
        });

        //Salvando alterações
        Categoria categoriaSalva = gateway.salvar(categoriaBuscada);

        log.info("Categoria alterada com sucesso! Categoria: {}", categoriaSalva.toString());
        return categoriaSalva;
    }

    public void deletarCategoria(UUID id) {
        log.info("Deletando categoria por id. Id: {}", id);

        buscarCategoriaPorId(id);
        gateway.deletar(id);

        log.info("Categoria deletada com sucesso!");
    }
}
