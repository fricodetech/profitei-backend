package com.whatsapp.financeiro.infrastructure.dataprovider;

import com.whatsapp.financeiro.builder.UsuarioBuilder;
import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.infrastructure.exceptions.DataProviderException;
import com.whatsapp.financeiro.infrastructure.repository.UsuarioRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioDataProviderTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioDataProvider dataProvider;

    private Usuario usuario;
    private UsuarioEntity usuarioEntity;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        usuario = UsuarioBuilder.criarUsuarioDomain();
        usuarioEntity = UsuarioBuilder.criarUsuarioEntity();

        usuarioId = usuario.getId();
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        usuario.setId(null);

        when(repository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        Usuario resultado = dataProvider.salvar(usuario);

        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        verify(repository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void deveLancarExcecaoAoSalvarUsuario() {
        when(repository.save(any(UsuarioEntity.class))).thenThrow(new RuntimeException("Erro DB"));

        DataProviderException ex = assertThrows(DataProviderException.class, () -> dataProvider.salvar(usuario));

        assertEquals(UsuarioDataProvider.MENSAGEM_ERRO_SALVAR_USUARIO, ex.getMessage());
        verify(repository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void deveConsultarUsuarioPorIdComSucesso() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(usuarioEntity));

        Optional<Usuario> resultado = dataProvider.consultarPorId(usuarioId);

        assertTrue(resultado.isPresent());
        assertEquals(usuarioId, resultado.get().getId());
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    void deveRetornarOptionalVazioQuandoNaoEncontrarUsuarioPorId() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Optional<Usuario> resultado = dataProvider.consultarPorId(usuarioId);

        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoConsultarUsuarioPorId() {
        when(repository.findById(any())).thenThrow(new RuntimeException("Erro DB"));

        DataProviderException ex = assertThrows(DataProviderException.class, () -> dataProvider.consultarPorId(usuarioId));

        assertEquals(UsuarioDataProvider.MENSAGEM_ERRO_CONSULTAR_POR_ID, ex.getMessage());
        verify(repository, times(1)).findById(any());
    }

    @Test
    void deveDeletarUsuarioComSucesso() {
        doNothing().when(repository).deleteById(any(UUID.class));

        dataProvider.deletar(usuarioId);

        verify(repository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoAoDeletarUsuario() {
        doThrow(new RuntimeException("Erro DB")).when(repository).deleteById(any());

        DataProviderException ex = assertThrows(DataProviderException.class, () -> dataProvider.deletar(usuarioId));

        assertEquals(UsuarioDataProvider.MENSAGEM_ERRO_DELETAR_POR_ID, ex.getMessage());
        verify(repository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void deveConsultarUsuarioPorTelefoneComSucesso() {
        when(repository.findByTelefone(anyString())).thenReturn(Optional.of(usuarioEntity));

        Optional<Usuario> resultado = dataProvider.consultarPorTelefone(usuario.getTelefone());

        assertTrue(resultado.isPresent());
        assertEquals(usuario.getTelefone(), resultado.get().getTelefone());
        verify(repository, times(1)).findByTelefone(anyString());
    }

    @Test
    void deveRetornarOptionalVazioQuandoNaoEncontrarUsuarioPorTelefone() {
        when(repository.findByTelefone(anyString())).thenReturn(Optional.empty());

        Optional<Usuario> resultado = dataProvider.consultarPorTelefone(usuario.getTelefone());

        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findByTelefone(anyString());
    }

    @Test
    void deveLancarExcecaoAoConsultarUsuarioPorTelefone() {
        when(repository.findByTelefone(usuario.getTelefone())).thenThrow(new RuntimeException("Erro DB"));

        DataProviderException ex = assertThrows(DataProviderException.class, () -> dataProvider.consultarPorTelefone(usuario.getTelefone()));

        assertEquals(UsuarioDataProvider.MENSAGEM_ERRO_CONSULTAR_POR_TELEFONE, ex.getMessage());
        verify(repository, times(1)).findByTelefone(usuario.getTelefone());
    }

}