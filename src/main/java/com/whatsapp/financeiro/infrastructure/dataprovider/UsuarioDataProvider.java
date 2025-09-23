package com.whatsapp.financeiro.infrastructure.dataprovider;

import com.whatsapp.financeiro.application.gateway.UsuarioGateway;
import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.infrastructure.exceptions.DataProviderException;
import com.whatsapp.financeiro.infrastructure.mapper.UsuarioMapperInfra;
import com.whatsapp.financeiro.infrastructure.repository.UsuarioRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioDataProvider implements UsuarioGateway {

    private final UsuarioRepository repository;

    private final String MENSAGEM_ERRO_SALVAR_USUARIO = "Erro ao salvar usuário.";
    private final String MENSAGEM_ERRO_CONSULTAR_POR_ID = "Erro ao consultar usuário por id.";
    private final String MENSAGEM_ERRO_DELETAR_POR_ID = "Erro ao deletar usuário pelo seu id.";
    private final String MENSAGEM_ERRO_CONSULTAR_POR_TELEFONE = "Erro ao consultar usuário pelo seu email.";

    @Override
    public Usuario salvar(Usuario novoUsuario) {
        UsuarioEntity usuario = UsuarioMapperInfra.paraEntity(novoUsuario);

        try {
            usuario = repository.save(usuario);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR_USUARIO, ex);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_USUARIO, ex.getCause());
        }

        return UsuarioMapperInfra.paraDomain(usuario);
    }

    @Override
    public Optional<Usuario> consultarPorId(UUID idUsuario) {
        Optional<UsuarioEntity> usuario;

        try {
            usuario = repository.findById(idUsuario);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex.getCause());
        }
        return usuario.map(UsuarioMapperInfra::paraDomain);
    }

    @Override
    public void deletar(UUID idUsuario) {
        try {
            repository.deleteById(idUsuario);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_DELETAR_POR_ID, ex);
            throw new DataProviderException(MENSAGEM_ERRO_DELETAR_POR_ID, ex.getCause());
        }
    }

    @Override
    public Optional<Usuario> consultarPorTelefone(String telefone) {
        Optional<UsuarioEntity> usuario;

        try {
            usuario = repository.findByTelefone(telefone);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_POR_TELEFONE, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_POR_TELEFONE, ex.getCause());
        }

        return usuario.map(UsuarioMapperInfra::paraDomain);
    }
}
