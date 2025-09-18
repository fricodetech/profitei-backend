package com.whatsapp.financeiro.application.gateway;

import com.whatsapp.financeiro.domain.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioGateway {
    Usuario salvar(Usuario novoUsuario);

    Optional<Usuario> consultarPorId(UUID idUsuario);

    void deletar(UUID idUsuario);

    Optional<Usuario> consultarPorTelefone(String telefone);
}
