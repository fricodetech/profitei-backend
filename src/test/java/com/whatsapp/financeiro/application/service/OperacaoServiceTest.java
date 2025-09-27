package com.whatsapp.financeiro.application.service;

import com.whatsapp.financeiro.application.exceptions.OperacaoNaoEncontradaException;
import com.whatsapp.financeiro.application.gateway.OperacaoGateway;
import com.whatsapp.financeiro.builder.CategoriaBuilder;
import com.whatsapp.financeiro.builder.OperacaoBuilder;
import com.whatsapp.financeiro.builder.UsuarioBuilder;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.domain.TipoOperacao;
import com.whatsapp.financeiro.domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class OperacaoServiceTest {

    @Mock
    private OperacaoGateway gateway;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private OperacaoService service;

    private Operacao operacaoTeste;
    private Usuario usuarioTeste;
    private Categoria categoriaTeste;
    private Pageable pageable;
    private UUID id;

    @BeforeEach
    void inicializar() {
        operacaoTeste = OperacaoBuilder.criarOperacaoDomain(TipoOperacao.GANHO);

        usuarioTeste = operacaoTeste.getUsuario();
        categoriaTeste = operacaoTeste.getCategoria();

        id = operacaoTeste.getId();
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void deveSalvarOperacaoComSucesso() {
        Mockito.when(categoriaService.consultarCategoriaPorId(Mockito.any(UUID.class))).thenReturn(categoriaTeste);
        Mockito.when(usuarioService.consultarPorId(Mockito.any(UUID.class))).thenReturn(usuarioTeste);
        Mockito.when(gateway.salvar(Mockito.any(Operacao.class))).thenReturn(operacaoTeste);

        Operacao resultado = service.salvarOperacao(operacaoTeste);

        Assertions.assertNotNull(resultado);
        Mockito.verify(categoriaService).consultarCategoriaPorId(Mockito.any(UUID.class));
        Mockito.verify(usuarioService).consultarPorId(Mockito.any(UUID.class));
        Mockito.verify(gateway).salvar(Mockito.any(Operacao.class));
    }

    @Test
    void deveConsultarOperacaoPorIdComSucesso() {
        Mockito.when(gateway.consultarPorId(Mockito.any(UUID.class))).thenReturn(Optional.of(operacaoTeste));

        Operacao resultado = service.consultarOperacaoPorId(id);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(operacaoTeste, resultado);
        Mockito.verify(gateway).consultarPorId(Mockito.any(UUID.class));
    }

    @Test
    void deveLancarExceptionQuandoOperacaoNaoEncontrada() {
        Mockito.when(gateway.consultarPorId(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(
                OperacaoNaoEncontradaException.class,
                () -> service.consultarOperacaoPorId(id)
        );

        Mockito.verify(gateway).consultarPorId(Mockito.any(UUID.class));
    }

    @Test
    void deveConsultarTodasOperacoesComSucesso() {
        Page<Operacao> pagina = OperacaoBuilder.criarPageDeOperacaoDomain();

        Mockito.when(usuarioService.consultarPorId(Mockito.any(UUID.class))).thenReturn(usuarioTeste);
        Mockito.when(gateway.consultarTodos(Mockito.any(UUID.class), Mockito.any(Pageable.class))).thenReturn(pagina);

        Page<Operacao> resultado = service.consultarTodasOperacoes(usuarioTeste.getId(), pageable);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(pagina.getTotalElements(), resultado.getTotalElements());
        Mockito.verify(usuarioService).consultarPorId(Mockito.any(UUID.class));
        Mockito.verify(gateway).consultarTodos(Mockito.any(UUID.class), Mockito.any(Pageable.class));
    }

    @Test
    void deveConsultarTodosGastosComSucesso() {
        Page<Operacao> pagina = OperacaoBuilder.criarPageDeGastoDomain();

        Mockito.when(usuarioService.consultarPorId(Mockito.any(UUID.class))).thenReturn(usuarioTeste);
        Mockito.when(gateway.consultarTodosPorTipoOperacao(Mockito.eq(TipoOperacao.GASTO),Mockito.any(UUID.class), Mockito.any(Pageable.class)))
                .thenReturn(pagina);

        Page<Operacao> resultado = service.consultarTodosGastos(usuarioTeste.getId(), pageable);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(pagina.getTotalElements(), resultado.getTotalElements());
        Mockito.verify(usuarioService).consultarPorId(Mockito.any(UUID.class));
        Mockito.verify(gateway).consultarTodosPorTipoOperacao(Mockito.eq(TipoOperacao.GASTO), Mockito.any(UUID.class), Mockito.any(Pageable.class));
    }

    @Test
    void deveConsultarTodosGanhosComSucesso() {
        Page<Operacao> pagina = OperacaoBuilder.criarPageDeGanhoDomain();

        Mockito.when(usuarioService.consultarPorId(Mockito.any(UUID.class))).thenReturn(usuarioTeste);
        Mockito.when(gateway.consultarTodosPorTipoOperacao(Mockito.eq(TipoOperacao.GANHO), Mockito.any(UUID.class), Mockito.any(Pageable.class)))
                .thenReturn(pagina);

        Page<Operacao> resultado = service.consultarTodosGanhos(usuarioTeste.getId(), pageable);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(pagina.getTotalElements(), resultado.getTotalElements());
        Mockito.verify(usuarioService).consultarPorId(Mockito.any(UUID.class));
        Mockito.verify(gateway).consultarTodosPorTipoOperacao(Mockito.eq(TipoOperacao.GANHO), Mockito.any(UUID.class), Mockito.any(Pageable.class));
    }

    @Test
    void deveAlterarOperacaoComSucesso() {
        Operacao operacaoNova = operacaoTeste;
        operacaoNova.setDescricao("Nova descrição");

        Mockito.when(gateway.consultarPorId(Mockito.any(UUID.class))).thenReturn(Optional.of(operacaoTeste));
        Mockito.when(categoriaService.consultarCategoriaPorId(Mockito.any(UUID.class))).thenReturn(categoriaTeste);
        Mockito.when(gateway.salvar(Mockito.any(Operacao.class))).thenReturn(operacaoTeste);

        Operacao resultado = service.alterarOperacao(id, operacaoNova);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Nova descrição", resultado.getDescricao());
        Mockito.verify(gateway).consultarPorId(Mockito.any(UUID.class));
        Mockito.verify(categoriaService).consultarCategoriaPorId(Mockito.any(UUID.class));
        Mockito.verify(gateway).salvar(Mockito.any(Operacao.class));
    }

    @Test
    void deveDeletarOperacaoComSucesso() {
        Mockito.when(gateway.consultarPorId(Mockito.any(UUID.class))).thenReturn(Optional.of(operacaoTeste));
        Mockito.doNothing().when(gateway).deletar(Mockito.any(UUID.class));

        service.deletarOperacao(id);

        Mockito.verify(gateway).consultarPorId(Mockito.any(UUID.class));
        Mockito.verify(gateway).deletar(Mockito.any(UUID.class));
    }
}