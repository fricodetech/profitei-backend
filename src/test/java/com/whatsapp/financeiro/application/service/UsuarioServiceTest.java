package com.whatsapp.financeiro.application.service;

import com.whatsapp.financeiro.application.exceptions.UsuarioJaCadastradoException;
import com.whatsapp.financeiro.application.exceptions.UsuarioNaoEncontradoException;
import com.whatsapp.financeiro.application.gateway.UsuarioGateway;
import com.whatsapp.financeiro.builder.UsuarioBuilder;
import com.whatsapp.financeiro.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioGateway gateway;

    @Mock
    private PlanoService planoService;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuarioDomainTeste;
    private UUID id;

    @BeforeEach
    void inicializar() {
        usuarioDomainTeste = UsuarioBuilder.criarUsuarioDomain();
        id = usuarioDomainTeste.getId();
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        when(gateway.consultarPorTelefone(anyString())).thenReturn(Optional.empty());
        when(planoService.consultarPlanoPorId(any(UUID.class))).thenReturn(usuarioDomainTeste.getPlano());
        when(gateway.salvar(any(Usuario.class))).thenReturn(usuarioDomainTeste);

        Usuario resultado = service.cadastrar(usuarioDomainTeste);

        assertNotNull(resultado);
        assertEquals(usuarioDomainTeste.getId(), resultado.getId());
        verify(gateway).consultarPorTelefone(usuarioDomainTeste.getTelefone());
        verify(gateway).salvar(usuarioDomainTeste);
    }

    @Test
    void deveLancarExcecaoAoCadastrarUsuarioJaExistente() {
        when(gateway.consultarPorTelefone(anyString())).thenReturn(Optional.of(usuarioDomainTeste));

        UsuarioJaCadastradoException ex = assertThrows(UsuarioJaCadastradoException.class, () -> service.cadastrar(usuarioDomainTeste));

        assertEquals("Usuário já cadastrado com este telefone.", ex.getMessage());
        verify(gateway).consultarPorTelefone(usuarioDomainTeste.getTelefone());
    }

    @Test
    void deveConsultarUsuarioPorIdComSucesso() {
        when(gateway.consultarPorId(any(UUID.class))).thenReturn(Optional.of(usuarioDomainTeste));

        Usuario resultado = service.consultarPorId(id);

        assertNotNull(resultado);
        assertEquals(usuarioDomainTeste.getNome(), resultado.getNome());
        verify(gateway).consultarPorId(id);
    }

    @Test
    void deveLancarExcecaoAoConsultarUsuarioPorIdNaoExistente() {
        when(gateway.consultarPorId(any(UUID.class))).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException ex = assertThrows(UsuarioNaoEncontradoException.class, () -> service.consultarPorId(id));

        assertEquals("Usuário não encontrado.", ex.getMessage());
        verify(gateway).consultarPorId(id);
    }

    @Test
    void deveAlterarUsuarioComSucesso() {
        Usuario novosDados = UsuarioBuilder.criarUsuarioDomain();
        novosDados.setNome("Nome atualizado");
        novosDados.setEmail("novoemail@gmail.com");

        when(service.consultarPorId(any(UUID.class))).thenReturn(usuarioDomainTeste);
        when(planoService.consultarPlanoPorId(any(UUID.class))).thenReturn(usuarioDomainTeste.getPlano());
        when(gateway.salvar(any(Usuario.class))).thenReturn(usuarioDomainTeste);

        Usuario resultado = service.alterar(id, novosDados);

        assertNotNull(resultado);
        assertEquals("Nome atualizado", usuarioDomainTeste.getNome());
        assertEquals("novoemail@gmail.com", usuarioDomainTeste.getEmail());
        verify(gateway).consultarPorId(id);
        verify(gateway).salvar(usuarioDomainTeste);
    }

    @Test
    void deveDeletarUsuarioComSucesso() {
        when(gateway.consultarPorId(any(UUID.class))).thenReturn(Optional.of(usuarioDomainTeste));
        doNothing().when(gateway).deletar(any(UUID.class));

        service.deletar(id);

        verify(gateway).consultarPorId(id);
        verify(gateway).deletar(id);
    }
}