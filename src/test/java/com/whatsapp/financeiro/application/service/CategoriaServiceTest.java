package com.whatsapp.financeiro.application.service;

import com.whatsapp.financeiro.application.exceptions.CategoriaNaoEncontradaException;
import com.whatsapp.financeiro.application.gateway.CategoriaGateway;
import com.whatsapp.financeiro.builder.CategoriaBuilder;
import com.whatsapp.financeiro.builder.UsuarioBuilder;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.domain.Usuario;
import com.whatsapp.financeiro.validators.CategoriaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaGateway gateway;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private CategoriaService service;

    private Categoria categoriaDomainTeste;
    private Pageable pageable;
    private UUID id;

    @BeforeEach
    void inicializar() {
        categoriaDomainTeste = CategoriaBuilder.criarCategoriaDomain();

        id = categoriaDomainTeste.getId();

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void deveCriarCategoriaComSucesso() {
        Mockito.when(usuarioService.consultarPorId(Mockito.any(UUID.class))).thenReturn(categoriaDomainTeste.getUsuario());
        Mockito.when(gateway.salvar(Mockito.any(Categoria.class))).thenReturn(categoriaDomainTeste);

        categoriaDomainTeste.setId(null);

        Categoria resultado = service.criarCategoria(categoriaDomainTeste);

        Assertions.assertNotNull(resultado);
        Mockito.verify(usuarioService).consultarPorId(Mockito.any(UUID.class));
        Mockito.verify(gateway).salvar(Mockito.any(Categoria.class));
    }

    @Test
    void deveBuscarTodasAsCategoriasComSucesso() {
        Page<Categoria> categoriaDomainPage = CategoriaBuilder.criarPageDeCategoriaDomain();
        Usuario usuarioTeste = UsuarioBuilder.criarUsuarioDomain();

        Mockito.when(usuarioService.consultarPorId(Mockito.any(UUID.class))).thenReturn(usuarioTeste);
        Mockito.when(gateway.consultarTodas(Mockito.any(UUID.class), Mockito.any(Pageable.class))).thenReturn(categoriaDomainPage);

        Page<Categoria> resultado = service.consultarTodasCategorias(usuarioTeste.getId(), pageable);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(categoriaDomainPage.getTotalElements(), resultado.getTotalElements());
        Mockito.verify(usuarioService).consultarPorId(Mockito.any(UUID.class));
        Mockito.verify(gateway.consultarTodas(Mockito.any(UUID.class), Mockito.any(Pageable.class)));
    }

    @Test
    void deveBuscarCategoriaPorIdComSucesso() {
        Mockito.when(gateway.consultarPorId(Mockito.any(UUID.class))).thenReturn(Optional.of(categoriaDomainTeste));

        Categoria resultado = service.consultarCategoriaPorId(id);

        Assertions.assertNotNull(resultado);
        Mockito.verify(gateway).consultarPorId(Mockito.any(UUID.class));
    }

    @Test
    void deveLancarExceptionCategoriaNaoEncontrada() {
        Mockito.when(gateway.consultarPorId(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        CategoriaNaoEncontradaException exception = Assertions.assertThrows(
                CategoriaNaoEncontradaException.class,
                () -> service.consultarCategoriaPorId(id));

        Assertions.assertEquals(CategoriaService.ERRO_CATEGORIA_NAO_ENCONTRADA, exception.getMessage());
    }

    @Test
    void deveAlterarCategoriaComSucesso() {
        Categoria categoriaNova = CategoriaBuilder.criarCategoriaDomain();
        categoriaNova.setTitulo("Novo título");
        categoriaNova.setDescricao("Nova descrição");

        Mockito.when(service.consultarCategoriaPorId(Mockito.any(UUID.class))).thenReturn(categoriaDomainTeste);
        Mockito.when(gateway.salvar(Mockito.any(Categoria.class))).thenReturn(categoriaDomainTeste);

        Categoria resultado = service.alterarCategoria(id, categoriaNova);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Novo título", resultado.getTitulo());
        Assertions.assertEquals("Nova descrição", resultado.getDescricao());

        Mockito.verify(gateway).consultarPorId(Mockito.any(UUID.class));
        Mockito.verify(gateway).salvar(Mockito.any(Categoria.class));
    }

    @Test
    void deveDeletarCategoriaComSucesso() {
        Mockito.when(service.consultarCategoriaPorId(Mockito.any(UUID.class))).thenReturn(categoriaDomainTeste);
        Mockito.doNothing().when(gateway).deletar(id);

        service.deletarCategoria(id);

        Mockito.verify(gateway).deletar(Mockito.any(UUID.class));
    }
}
