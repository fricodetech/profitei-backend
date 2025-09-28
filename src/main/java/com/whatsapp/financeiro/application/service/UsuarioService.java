package com.whatsapp.financeiro.application.service;

import com.whatsapp.financeiro.application.exceptions.UsuarioJaCadastradoException;
import com.whatsapp.financeiro.application.exceptions.UsuarioNaoEncontradoException;
import com.whatsapp.financeiro.application.gateway.UsuarioGateway;
import com.whatsapp.financeiro.domain.Plano;
import com.whatsapp.financeiro.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioGateway gateway;
    private final PlanoService planoService;

    public Usuario cadastrar(Usuario novoUsuario) {
        log.info("Cadastrando novo usuário. Usuario: {}", novoUsuario);

        Optional<Usuario> usuarioExistente = this.consultarPorTelefone(novoUsuario.getTelefone());

        if(usuarioExistente.isPresent()) {
            throw new UsuarioJaCadastradoException();
        }

        Plano plano = planoService.consultarPlanoPorId(novoUsuario.getPlano().getId());
        novoUsuario.setPlano(plano);

        Usuario usuarioSalvo = gateway.salvar(novoUsuario);

        log.info("Novo usuário cadastrado com sucesso. Usuário: {}", usuarioSalvo);

        return usuarioSalvo;
    }

    public Usuario consultarPorId(UUID idUsuario) {
        Optional<Usuario> usuario = gateway.consultarPorId(idUsuario);

        if(usuario.isEmpty()) {
            throw new UsuarioNaoEncontradoException();
        }

        return usuario.get();
    }

    public Usuario alterar(UUID idUsuario, Usuario novosDados) {
        Usuario usuario = this.consultarPorId(idUsuario);

        Plano plano = planoService.consultarPlanoPorId(novosDados.getId());
        novosDados.setPlano(plano);

        usuario.setDados(novosDados);

        return gateway.salvar(usuario);
    }

    public void deletar(UUID idUsuario) {
        this.consultarPorId(idUsuario);
        gateway.deletar(idUsuario);
    }

    private Optional<Usuario> consultarPorTelefone(String telefone) {
        return gateway.consultarPorTelefone(telefone);
    }
}
