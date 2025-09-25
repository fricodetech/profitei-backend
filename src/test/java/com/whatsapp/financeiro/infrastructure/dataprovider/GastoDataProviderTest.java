package com.whatsapp.financeiro.infrastructure.dataprovider;

import com.whatsapp.financeiro.builder.GastoBuilder;
import com.whatsapp.financeiro.domain.Operacao;
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
class GastoDataProviderTest {

    @Mock
    private OperacaoRepository repository;

    @InjectMocks
    private OperacaoDataProvider dataProvider;

    private Operacao gastoDomainTeste;
    private OperacaoEntity gastoEntityTeste;
    private Pageable pageable;
    private UUID id;

    @BeforeEach
    void inicializar() {
        gastoDomainTeste = GastoBuilder.criarGastoDomain();
        gastoEntityTeste = GastoBuilder.criarGastoEntity();

        pageable = PageRequest.of(0, 10);
        id = gastoDomainTeste.getId();
    }

    @Test
    void deveSalvarGastoComSucesso() {
        gastoDomainTeste.setId(null);

        Mockito.when(repository.save(Mockito.any())).thenReturn(gastoEntityTeste);

        Operacao resultado = dataProvider.salvar(gastoDomainTeste);

        Assertions.assertNotNull(resultado.getId());
    }

    @Test
    void deveLancarExceptionAoSalvar() {
        Mockito.when(repository.save(Mockito.any())).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.salvar(gastoDomainTeste));

        Assertions.assertEquals(OperacaoDataProvider.MENSAGEM_ERRO_SALVAR_OPERACAO, exception.getMessage());
    }

    @Test
    void deveBuscarTodosGastosComSucesso() {
        Page<Operacao> gastoPage = GastoBuilder.criarPageDeGastoDomain();

        Mockito.when(repository.findAll(pageable)).thenReturn(gastoPage.map(OperacaoMapperInfra::paraEntity));

        Page<Operacao> resultado = dataProvider.consultarTodos(pageable);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(gastoPage.getTotalElements(), resultado.getTotalElements());
    }

    @Test
    void deveLancarExceptionAoBuscarTodosGastos() {
        Mockito.when(repository.findAll(pageable)).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.consultarTodos(pageable));

        Assertions.assertEquals(OperacaoDataProvider.MENSAGEM_ERRO_BUSCAR_OPERACOES, exception.getMessage());
    }

    @Test
    void deveBuscarGastoPorIdComSucesso() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(gastoEntityTeste));

        Optional<Operacao> resultado = dataProvider.consultarPorId(id);

        Assertions.assertTrue(resultado.isPresent());
    }

    @Test
    void deveLancarExceptionAoBuscarPorId() {
        Mockito.when(repository.findById(id)).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.consultarPorId(id));

        Assertions.assertEquals(OperacaoDataProvider.MENSAGEM_ERRO_BUSCAR_OPERACAO_POR_ID, exception.getMessage());
    }

    @Test
    void deveDeletarGastoComSucesso() {
        Mockito.doNothing().when(repository).deleteById(id);

        dataProvider.deletar(id);

        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void deveLancarExceptionAoDeletarGasto() {
        Mockito.doThrow(RuntimeException.class).when(repository).deleteById(id);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.deletar(id));

        Assertions.assertEquals(OperacaoDataProvider.MENSAGEM_ERRO_DELETAR_OPERACAO, exception.getMessage());
    }
}

