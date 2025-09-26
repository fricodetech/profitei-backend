package com.whatsapp.financeiro.application.service;

import com.whatsapp.financeiro.application.exceptions.OperacaoNaoEncontradaException;
import com.whatsapp.financeiro.application.gateway.OperacaoGateway;
import com.whatsapp.financeiro.builder.OperacaoBuilder;
import com.whatsapp.financeiro.domain.Operacao;
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
public class GastoServiceTest {

    @Mock
    private OperacaoGateway gateway;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private CategoriaService categoriaService;

    @Captor
    private ArgumentCaptor<Operacao> captor;

    @InjectMocks
    private OperacaoService service;

    private Operacao gastoDomainTeste;
    private Pageable pageable;
    private UUID id;

    @BeforeEach
    void inicializar() {
        gastoDomainTeste = OperacaoBuilder.criarOperacaoDomain();

        id = gastoDomainTeste.getId();
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void deveSalvarGastoComSucesso() {
        Mockito.when(usuarioService.consultarPorId(Mockito.any())).thenReturn(gastoDomainTeste.getUsuario());
        Mockito.when(categoriaService.buscarCategoriaPorId(Mockito.any())).thenReturn(gastoDomainTeste.getCategoria());
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(gastoDomainTeste);

        gastoDomainTeste.setId(null);

        Operacao resultado = service.salvarOperacao(gastoDomainTeste);
        Operacao gastoCapturado = captor.getValue();

        Assertions.assertEquals(gastoCapturado.getId(), resultado.getId());
        Mockito.verify(usuarioService).consultarPorId(Mockito.any());
        Mockito.verify(categoriaService).buscarCategoriaPorId(Mockito.any());
        Mockito.verify(gateway).salvar(Mockito.any());
    }

    @Test
    void deveBuscarTodosOsGastosComSucesso() {
        Page<Operacao> gastoDomainPage = OperacaoBuilder.criarPageDeGastoDomain();
        Mockito.when(gateway.consultarTodos(Mockito.any())).thenReturn(gastoDomainPage);

        Page<Operacao> resultado = service.consultarTodasOperacoes(pageable);

        resultado.forEach(gasto -> Assertions.assertNotNull(gasto.getId()));
        Mockito.verify(gateway).consultarTodos(Mockito.any());
    }

    @Test
    void deveBuscarGastoPorIdComSucesso() {
        Mockito.when(gateway.consultarPorId(Mockito.any())).thenReturn(Optional.of(gastoDomainTeste));

        Operacao resultado = service.consultarOperacaoPorId(id);

        Assertions.assertNotNull(resultado.getId());
        Mockito.verify(gateway).consultarPorId(Mockito.any());
    }

    @Test
    void deveLancarExceptionGastoNaoEncontrado() {
        Mockito.when(gateway.consultarPorId(Mockito.any())).thenReturn(Optional.empty());

        OperacaoNaoEncontradaException exception = Assertions.assertThrows(
                OperacaoNaoEncontradaException.class,
                () -> service.consultarOperacaoPorId(id));

        Assertions.assertEquals("Gasto não encontrado.", exception.getMessage());
    }

    @Test
    void deveAlterarGastoComSucesso() {
        Operacao novosDados = gastoDomainTeste;

        novosDados.setValor(BigDecimal.valueOf(15));

        Mockito.when(gateway.consultarPorId(Mockito.any())).thenReturn(Optional.of(gastoDomainTeste));
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(novosDados);

        Operacao resultado = service.alterarOperacao(id, novosDados);
        Operacao gastoCapturado = captor.getValue();

        Assertions.assertEquals(BigDecimal.valueOf(15), gastoCapturado.getValor());
    }

    @Test
    void deveDeletarGastoComSucesso() {
        Mockito.when(gateway.consultarPorId(Mockito.any())).thenReturn(Optional.of(gastoDomainTeste));
        Mockito.doNothing().when(gateway).deletar(id);

        service.deletarOperacao(id);

        Mockito.verify(gateway).deletar(id);
    }
}


