package com.whatsapp.financeiro.application.service;

import com.whatsapp.financeiro.application.exceptions.GastoNaoEncontradoException;
import com.whatsapp.financeiro.application.gateway.GastoGateway;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.domain.Gasto;
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
public class GastoService {

    private final GastoGateway gateway;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;

    public Gasto salvarGasto(Gasto gastoNovo) {
        log.info("Iniciando processo de salvamento. Gasto: {}", gastoNovo);

        Categoria categoriaConsultada = categoriaService.buscarCategoriaPorId(gastoNovo.getCategoria().getId());
        gastoNovo.setCategoria(categoriaConsultada);

        Usuario usuarioConsultado = usuarioService.consultarPorId(gastoNovo.getUsuario().getId());
        gastoNovo.setUsuario(usuarioConsultado);

        Gasto gastoSalvo = gateway.salvar(gastoNovo);

        log.info("Gasto salvo com sucesso. Gasto: {}", gastoSalvo);
        return gastoSalvo;
    }

    public Gasto consultarGastoPorId(UUID id) {

        Optional<Gasto> gastoOptional = gateway.consultarPorId(id);

        if (gastoOptional.isEmpty()) {
            throw new GastoNaoEncontradoException();
        }

        return gastoOptional.get();
    }

    public Page<Gasto> consultarTodosGastos(Pageable pageable) {

        Page<Gasto> gastoPage = gateway.consultarTodos(pageable);

        return gastoPage;
    }

    public Gasto alterarGasto(UUID id, Gasto gastoNovo) {

        Gasto gastoConsultado = consultarGastoPorId(id);

        gastoConsultado.alterarDados(gastoNovo);

        Gasto gastoSalvo = gateway.salvar(gastoConsultado);

        return gastoSalvo;
    }

    public void deletarGasto(UUID id) {

        consultarGastoPorId(id);
        gateway.deletar(id);
    }
}
