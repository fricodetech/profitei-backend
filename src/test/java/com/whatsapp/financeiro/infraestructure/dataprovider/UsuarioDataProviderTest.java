package com.whatsapp.financeiro.infraestructure.dataprovider;

import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.infraestructure.exceptions.DataProviderException;
import com.whatsapp.financeiro.infraestructure.mapper.UsuarioMapperInfra;
import com.whatsapp.financeiro.infraestructure.repository.UsuarioRepository;
import com.whatsapp.financeiro.infraestructure.repository.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        usuarioId = UUID.randomUUID();
        usuario = Usuario.builder()
                .id(usuarioId)
                .nome("Vitor")
                .email("vitor@email.com")
                .telefone("11999999999")
                .build();

        usuarioEntity = UsuarioMapperInfra.paraEntity(usuario);
    }

    // --- salvar() ---
    @Test
    void deveSalvarUsuarioComSucesso() {
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

        assertEquals("Erro ao salvar usuário.", ex.getMessage());
        verify(repository, times(1)).save(any(UsuarioEntity.class));
    }

    // --- consultarPorId() ---
    @Test
    void deveConsultarUsuarioPorIdComSucesso() {
        when(repository.findById(usuarioId)).thenReturn(Optional.of(usuarioEntity));

        Optional<Usuario> resultado = dataProvider.consultarPorId(usuarioId);

        assertTrue(resultado.isPresent());
        assertEquals(usuarioId, resultado.get().getId());
        verify(repository, times(1)).findById(usuarioId);
    }

    @Test
    void deveRetornarOptionalVazioQuandoNaoEncontrarUsuarioPorId() {
        when(repository.findById(usuarioId)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = dataProvider.consultarPorId(usuarioId);

        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findById(usuarioId);
    }

    @Test
    void deveLancarExcecaoAoConsultarUsuarioPorId() {
        when(repository.findById(usuarioId)).thenThrow(new RuntimeException("Erro DB"));

        DataProviderException ex = assertThrows(DataProviderException.class, () -> dataProvider.consultarPorId(usuarioId));

        assertEquals("Erro ao consultar usuário por id.", ex.getMessage());
        verify(repository, times(1)).findById(usuarioId);
    }

    // --- deletar() ---
    @Test
    void deveDeletarUsuarioComSucesso() {
        doNothing().when(repository).deleteById(usuarioId);

        dataProvider.deletar(usuarioId);

        verify(repository, times(1)).deleteById(usuarioId);
    }

    @Test
    void deveLancarExcecaoAoDeletarUsuario() {
        doThrow(new RuntimeException("Erro DB")).when(repository).deleteById(usuarioId);

        DataProviderException ex = assertThrows(DataProviderException.class, () -> dataProvider.deletar(usuarioId));

        assertEquals("Erro ao deletar usuário pelo seu id.", ex.getMessage());
        verify(repository, times(1)).deleteById(usuarioId);
    }

    // --- consultarPorTelefone() ---
    @Test
    void deveConsultarUsuarioPorTelefoneComSucesso() {
        when(repository.findByTelefone(usuario.getTelefone())).thenReturn(Optional.of(usuarioEntity));

        Optional<Usuario> resultado = dataProvider.consultarPorTelefone(usuario.getTelefone());

        assertTrue(resultado.isPresent());
        assertEquals(usuario.getTelefone(), resultado.get().getTelefone());
        verify(repository, times(1)).findByTelefone(usuario.getTelefone());
    }

    @Test
    void deveRetornarOptionalVazioQuandoNaoEncontrarUsuarioPorTelefone() {
        when(repository.findByTelefone(usuario.getTelefone())).thenReturn(Optional.empty());

        Optional<Usuario> resultado = dataProvider.consultarPorTelefone(usuario.getTelefone());

        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findByTelefone(usuario.getTelefone());
    }

    @Test
    void deveLancarExcecaoAoConsultarUsuarioPorTelefone() {
        when(repository.findByTelefone(usuario.getTelefone())).thenThrow(new RuntimeException("Erro DB"));

        DataProviderException ex = assertThrows(DataProviderException.class, () -> dataProvider.consultarPorTelefone(usuario.getTelefone()));

        assertEquals("Erro ao consultar usuário pelo seu email.", ex.getMessage());
        verify(repository, times(1)).findByTelefone(usuario.getTelefone());
    }

}