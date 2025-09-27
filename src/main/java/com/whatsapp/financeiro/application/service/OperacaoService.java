package com.whatsapp.financeiro.application.service;

import com.whatsapp.financeiro.application.exceptions.OperacaoNaoEncontradaException;
import com.whatsapp.financeiro.application.gateway.OperacaoGateway;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.domain.TipoOperacao;
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
public class OperacaoService {

    private final OperacaoGateway gateway;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;

    public Operacao salvarOperacao(Operacao operacaoNova) {
        log.info("Iniciando processo de salvamento. Operacao: {}", operacaoNova);

        Categoria categoriaConsultada = categoriaService.consultarCategoriaPorId(operacaoNova.getCategoria().getId());
        operacaoNova.setCategoria(categoriaConsultada);

        Usuario usuarioConsultado = usuarioService.consultarPorId(operacaoNova.getUsuario().getId());
        operacaoNova.setUsuario(usuarioConsultado);

        Operacao operacaoSalva = gateway.salvar(operacaoNova);

        log.info("Gasto salvo com sucesso. Gasto: {}", operacaoSalva);
        return operacaoSalva;
    }

    public Operacao consultarOperacaoPorId(UUID id) {

        Optional<Operacao> operacaoOptional = gateway.consultarPorId(id);

        if (operacaoOptional.isEmpty()) {
            throw new OperacaoNaoEncontradaException();
        }

        return operacaoOptional.get();
    }

    public Page<Operacao> consultarTodasOperacoes(UUID idUsuario, Pageable pageable) {

        usuarioService.consultarPorId(idUsuario);

        Page<Operacao> operacaoPage = gateway.consultarTodos(idUsuario, pageable);

        return operacaoPage;
    }

    public Page<Operacao> consultarTodosGastos(UUID idUsuario, Pageable pageable) {

        usuarioService.consultarPorId(idUsuario);

        Page<Operacao> operacaoPage = gateway.consultarTodosPorTipoOperacao(TipoOperacao.GASTO, idUsuario, pageable);

        return operacaoPage;
    }

    public Page<Operacao> consultarTodosGanhos(UUID idUsuario, Pageable pageable) {

        usuarioService.consultarPorId(idUsuario);

        Page<Operacao> operacaoPage = gateway.consultarTodosPorTipoOperacao(TipoOperacao.GANHO, idUsuario, pageable);

        return operacaoPage;
    }

    public Operacao alterarOperacao(UUID id, Operacao operacaoNova) {

        Operacao operacaoConsultada = consultarOperacaoPorId(id);
        UUID idCategoria = operacaoNova.getCategoria().getId();

        Categoria categoriaNova = categoriaService.consultarCategoriaPorId(idCategoria);
        operacaoNova.setCategoria(categoriaNova);

        operacaoConsultada.alterarDados(operacaoNova);

        Operacao operacaoSalva = gateway.salvar(operacaoConsultada);

        return operacaoSalva;
    }

    public void deletarOperacao(UUID id) {

        consultarOperacaoPorId(id);
        gateway.deletar(id);
    }
}
