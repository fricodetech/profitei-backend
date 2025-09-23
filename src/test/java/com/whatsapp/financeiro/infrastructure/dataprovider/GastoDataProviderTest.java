package com.whatsapp.financeiro.infrastructure.dataprovider;

import com.whatsapp.financeiro.builder.GastoBuilder;
import com.whatsapp.financeiro.domain.Gasto;
import com.whatsapp.financeiro.infrastructure.exceptions.DataProviderException;
import com.whatsapp.financeiro.infrastructure.mapper.GastoMapperInfra;
import com.whatsapp.financeiro.infrastructure.repository.GastoRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.GastoEntity;
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
    private GastoRepository repository;

    @InjectMocks
    private GastoDataProvider dataProvider;

    private Gasto gastoDomainTeste;
    private GastoEntity gastoEntityTeste;
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

        Gasto resultado = dataProvider.salvar(gastoDomainTeste);

        Assertions.assertNotNull(resultado.getId());
    }

    @Test
    void deveLancarExceptionAoSalvar() {
        Mockito.when(repository.save(Mockito.any())).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.salvar(gastoDomainTeste));

        Assertions.assertEquals(GastoDataProvider.MENSAGEM_ERRO_SALVAR_GASTO, exception.getMessage());
    }

    @Test
    void deveBuscarTodosGastosComSucesso() {
        Page<Gasto> gastoPage = GastoBuilder.criarPageDeGastoDomain();

        Mockito.when(repository.findAll(pageable)).thenReturn(gastoPage.map(GastoMapperInfra::paraEntity));

        Page<Gasto> resultado = dataProvider.consultarTodos(pageable);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(gastoPage.getTotalElements(), resultado.getTotalElements());
    }

    @Test
    void deveLancarExceptionAoBuscarTodosGastos() {
        Mockito.when(repository.findAll(pageable)).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.consultarTodos(pageable));

        Assertions.assertEquals(GastoDataProvider.MENSAGEM_ERRO_BUSCAR_GASTOS, exception.getMessage());
    }

    @Test
    void deveBuscarGastoPorIdComSucesso() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(gastoEntityTeste));

        Optional<Gasto> resultado = dataProvider.consultarPorId(id);

        Assertions.assertTrue(resultado.isPresent());
    }

    @Test
    void deveLancarExceptionAoBuscarPorId() {
        Mockito.when(repository.findById(id)).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.consultarPorId(id));

        Assertions.assertEquals(GastoDataProvider.MENSAGEM_ERRO_BUSCAR_GASTO_POR_ID, exception.getMessage());
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

        Assertions.assertEquals(GastoDataProvider.MENSAGEM_ERRO_DELETAR_GASTO, exception.getMessage());
    }
}

