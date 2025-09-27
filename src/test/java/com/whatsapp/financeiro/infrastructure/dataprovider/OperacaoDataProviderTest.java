package com.whatsapp.financeiro.infrastructure.dataprovider;

import com.whatsapp.financeiro.builder.OperacaoBuilder;
import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.domain.TipoOperacao;
import com.whatsapp.financeiro.infrastructure.exceptions.DataProviderException;
import com.whatsapp.financeiro.infrastructure.mapper.OperacaoMapperInfra;
import com.whatsapp.financeiro.infrastructure.repository.OperacaoRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.OperacaoEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class OperacaoDataProviderTest {

    @Mock
    private OperacaoRepository repository;

    @InjectMocks
    private OperacaoDataProvider dataProvider;

    private Operacao operacaoDomainTeste;
    private OperacaoEntity operacaoEntityTeste;
    private Pageable pageable;
    private UUID id;
    private UUID idUsuario;

    @BeforeEach
    void inicializar() {
        operacaoDomainTeste = OperacaoBuilder.criarOperacaoDomain(TipoOperacao.GANHO);
        operacaoEntityTeste = OperacaoBuilder.criarOperacaoEntity(TipoOperacao.GANHO);

        pageable = PageRequest.of(0, 10);
        id = operacaoDomainTeste.getId();
        idUsuario = operacaoDomainTeste.getUsuario().getId();
    }

    @Test
    void deveSalvarOperacaoComSucesso() {
        operacaoDomainTeste.setId(null);

        Mockito.when(repository.save(Mockito.any(OperacaoEntity.class))).thenReturn(operacaoEntityTeste);

        Operacao resultado = dataProvider.salvar(operacaoDomainTeste);

        Assertions.assertNotNull(resultado.getId());
        Mockito.verify(repository).save(Mockito.any(OperacaoEntity.class));
    }

    @Test
    void deveLancarExceptionAoSalvarOperacao() {
        Mockito.when(repository.save(Mockito.any())).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.salvar(operacaoDomainTeste));

        Assertions.assertEquals(OperacaoDataProvider.MENSAGEM_ERRO_SALVAR_OPERACAO, exception.getMessage());
    }

    @Test
    void deveBuscarTodasOperacoesComSucesso() {
        Page<Operacao> operacaoPage = OperacaoBuilder.criarPageDeOperacaoDomain();

        Mockito.when(repository.findAllByUsuarioId(Mockito.any(UUID.class), Mockito.any(Pageable.class))).thenReturn(operacaoPage.map(OperacaoMapperInfra::paraEntity));

        Page<Operacao> resultado = dataProvider.consultarTodos(idUsuario, pageable);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(operacaoPage.getTotalElements(), resultado.getTotalElements());
        Mockito.verify(repository).findAllByUsuarioId(Mockito.any(UUID.class), Mockito.any(Pageable.class));
    }

    @Test
    void deveLancarExceptionAoBuscarTodasOperacoes() {
        Mockito.when(repository.findAllByUsuarioId(Mockito.any(), Mockito.any())).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.consultarTodos(idUsuario, pageable));

        Assertions.assertEquals(OperacaoDataProvider.MENSAGEM_ERRO_BUSCAR_OPERACOES, exception.getMessage());
    }

    @Test
    void deveBuscarTodosGanhosComSucesso() {
        Page<Operacao> operacaoPage = OperacaoBuilder.criarPageDeGanhoDomain();

        Mockito.when(repository.findAllByTipoOperacaoAndUsuarioId(Mockito.any(TipoOperacao.class), Mockito.any(UUID.class), Mockito.any(Pageable.class)))
                .thenReturn(operacaoPage.map(OperacaoMapperInfra::paraEntity));

        Page<Operacao> resultado = dataProvider.consultarTodosPorTipoOperacao(TipoOperacao.GANHO, idUsuario, pageable);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(operacaoPage.getTotalElements(), resultado.getTotalElements());
        Mockito.verify(repository).findAllByTipoOperacaoAndUsuarioId(Mockito.any(TipoOperacao.class), Mockito.any(UUID.class), Mockito.any(Pageable.class));
    }

    @Test
    void deveBuscarTodosGastosComSucesso() {
        Page<Operacao> operacaoPage = OperacaoBuilder.criarPageDeGastoDomain();

        Mockito.when(repository.findAllByTipoOperacaoAndUsuarioId(Mockito.any(TipoOperacao.class), Mockito.any(UUID.class), Mockito.any(Pageable.class))).thenReturn(operacaoPage.map(OperacaoMapperInfra::paraEntity));

        Page<Operacao> resultado = dataProvider.consultarTodosPorTipoOperacao(TipoOperacao.GASTO, idUsuario, pageable);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(operacaoPage.getTotalElements(), resultado.getTotalElements());
        Mockito.verify(repository).findAllByTipoOperacaoAndUsuarioId(Mockito.any(TipoOperacao.class), Mockito.any(UUID.class), Mockito.any(Pageable.class));
    }

    @Test
    void deveLancarExceptionAoBuscarTodosGastosOuGanhos() {
        Mockito.when(repository.findAllByTipoOperacaoAndUsuarioId(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.consultarTodosPorTipoOperacao(TipoOperacao.GANHO, idUsuario, pageable));

        Assertions.assertEquals(OperacaoDataProvider.MENSAGEM_ERRO_BUSCAR_OPERACOES, exception.getMessage());
    }

    @Test
    void deveBuscarOperacaoPorIdComSucesso() {
        Mockito.when(repository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(operacaoEntityTeste));

        Optional<Operacao> resultado = dataProvider.consultarPorId(id);

        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(id, resultado.get().getId());
        Mockito.verify(repository).findById(Mockito.any());
    }

    @Test
    void deveLancarExceptionAoBuscarOperacaoPorId() {
        Mockito.when(repository.findById(id)).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.consultarPorId(id));

        Assertions.assertEquals(OperacaoDataProvider.MENSAGEM_ERRO_BUSCAR_OPERACAO_POR_ID, exception.getMessage());
    }

    @Test
    void deveDeletarOperacaoComSucesso() {
        Mockito.doNothing().when(repository).deleteById(Mockito.any(UUID.class));

        dataProvider.deletar(id);

        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void deveLancarExceptionAoDeletarOperacao() {
        Mockito.doThrow(RuntimeException.class).when(repository).deleteById(id);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.deletar(id));

        Assertions.assertEquals(OperacaoDataProvider.MENSAGEM_ERRO_DELETAR_OPERACAO, exception.getMessage());
    }
}

