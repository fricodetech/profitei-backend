package com.whatsapp.financeiro.application.service;

import com.whatsapp.financeiro.application.exceptions.UsuarioJaCadastradoException;
import com.whatsapp.financeiro.application.exceptions.UsuarioNaoEncontradoException;
import com.whatsapp.financeiro.application.gateway.UsuarioGateway;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioGateway gateway;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        usuario = Usuario.builder()
                .id(usuarioId)
                .nome("Vitor")
                .email("vitor@email.com")
                .telefone("11999999999")
                .build();
    }

    // --- cadastrar() ---
    @Test
    void deveCadastrarUsuarioComSucesso() {
        when(gateway.consultarPorTelefone(usuario.getTelefone())).thenReturn(Optional.empty());
        when(gateway.salvar(usuario)).thenReturn(usuario);

        Usuario resultado = service.cadastrar(usuario);

        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getId());
        verify(gateway, times(1)).consultarPorTelefone(usuario.getTelefone());
        verify(gateway, times(1)).salvar(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioJaExistirAoCadastrar() {
        when(gateway.consultarPorTelefone(usuario.getTelefone())).thenReturn(Optional.of(usuario));

        assertThrows(UsuarioJaCadastradoException.class, () -> service.cadastrar(usuario));

        verify(gateway, times(1)).consultarPorTelefone(usuario.getTelefone());
        verify(gateway, never()).salvar(any());
    }

    // --- consultarPorId() ---
    @Test
    void deveConsultarUsuarioPorIdComSucesso() {
        when(gateway.consultarPorId(usuarioId)).thenReturn(Optional.of(usuario));

        Usuario resultado = service.consultarPorId(usuarioId);

        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getId());
        verify(gateway, times(1)).consultarPorId(usuarioId);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoPorId() {
        when(gateway.consultarPorId(usuarioId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> service.consultarPorId(usuarioId));

        verify(gateway, times(1)).consultarPorId(usuarioId);
    }

    // --- alterar() ---
    @Test
    void deveAlterarUsuarioComSucesso() {
        Usuario novosDados = Usuario.builder()
                .id(usuarioId)
                .nome("Novo Nome")
                .email("novo@email.com")
                .telefone("11888888888")
                .build();

        when(gateway.consultarPorId(usuarioId)).thenReturn(Optional.of(usuario));
        when(gateway.salvar(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = service.alterar(usuarioId, novosDados);

        assertEquals("Novo Nome", resultado.getNome());
        assertEquals("novo@email.com", resultado.getEmail());
        assertEquals("11888888888", resultado.getTelefone());
        verify(gateway, times(1)).consultarPorId(usuarioId);
        verify(gateway, times(1)).salvar(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoAoAlterarUsuarioNaoEncontrado() {
        when(gateway.consultarPorId(usuarioId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> service.alterar(usuarioId, usuario));

        verify(gateway, times(1)).consultarPorId(usuarioId);
        verify(gateway, never()).salvar(any());
    }

    // --- deletar() ---
    @Test
    void deveDeletarUsuarioComSucesso() {
        when(gateway.consultarPorId(usuarioId)).thenReturn(Optional.of(usuario));
        doNothing().when(gateway).deletar(usuarioId);

        service.deletar(usuarioId);

        verify(gateway, times(1)).consultarPorId(usuarioId);
        verify(gateway, times(1)).deletar(usuarioId);
    }

    @Test
    void deveLancarExcecaoAoDeletarUsuarioNaoEncontrado() {
        when(gateway.consultarPorId(usuarioId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> service.deletar(usuarioId));

        verify(gateway, times(1)).consultarPorId(usuarioId);
        verify(gateway, never()).deletar(usuarioId);
    }
}