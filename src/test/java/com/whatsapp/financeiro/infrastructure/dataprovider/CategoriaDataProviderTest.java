package com.whatsapp.financeiro.infrastructure.dataprovider;

import com.whatsapp.financeiro.builder.CategoriaBuilder;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.infrastructure.exceptions.DataProviderException;
import com.whatsapp.financeiro.infrastructure.mapper.CategoriaMapperInfra;
import com.whatsapp.financeiro.infrastructure.repository.CategoriaRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import com.whatsapp.financeiro.validators.CategoriaValidator;
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
public class CategoriaDataProviderTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaDataProvider dataProvider;

    private Categoria categoriaDomainTeste;
    private CategoriaEntity categoriaEntityTeste;
    private Pageable pageable;
    private UUID id;

    @BeforeEach
    void inicializar() {
        categoriaDomainTeste = CategoriaBuilder.criarCategoriaDomain();
        categoriaEntityTeste = CategoriaBuilder.criarCategoriaEntity();

        pageable = PageRequest.of(0,10);

        id = categoriaDomainTeste.getId();
    }

    @Test
    void deveSalvarCategoriaComSucesso() {
        categoriaDomainTeste.setId(null);

        Mockito.when(repository.save(Mockito.any())).thenReturn(categoriaEntityTeste);

        Categoria categoriaResultado = dataProvider.salvar(categoriaDomainTeste);

        Assertions.assertNotNull(categoriaResultado.getId());
        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void deveLancarExceptionAoSalvar() {
        Mockito.when(repository.save(Mockito.any())).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.salvar(categoriaDomainTeste));

        Assertions.assertEquals(CategoriaDataProvider.MENSAGEM_ERRO_SALVAR_CATEGORIA, exception.getMessage());
    }

    @Test
    void deveBuscarTodasCategoriasComSucesso() {
        Page<Categoria> categoriaDomainPage = CategoriaBuilder.criarPageDeCategoria();
        Page<CategoriaEntity> categoriaEntityPage = categoriaDomainPage.map(CategoriaMapperInfra::paraEntity);

        Mockito.when(repository.findAllByUsuarioId(Mockito.any(), Mockito.any())).thenReturn(categoriaEntityPage);

        UUID idUsuario = categoriaEntityPage.getContent().getFirst().getId();
        Page<Categoria> resultado = dataProvider.consultarTodas(idUsuario, pageable);

        Assertions.assertNotNull(resultado);
        Assertions.assertNotNull(resultado.getContent().getFirst().getId());
        Assertions.assertEquals(categoriaDomainPage.getTotalElements(), resultado.getTotalElements());
        Mockito.verify(repository).findAllByUsuarioId(Mockito.any(), Mockito.any());
    }

    @Test
    void deveLancarExceptionAoBuscarTodasCategorias() {
        Mockito.when(repository.findAllByUsuarioId(Mockito.any(),Mockito.any())).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.consultarTodas(UUID.randomUUID(), pageable));

        Assertions.assertEquals(CategoriaDataProvider.MENSAGEM_ERRO_BUSCAR_CATEGORIAS, exception.getMessage());
    }

    @Test
    void deveBuscarCategoriaPorIdComSucesso() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(categoriaEntityTeste));

        Optional<Categoria> resultadoOptional = dataProvider.consultarPorId(id);
        Categoria resultado = resultadoOptional.get();

        Assertions.assertNotNull(resultado.getId());
        Assertions.assertEquals(id, resultado.getId());
        Mockito.verify(repository).findById(Mockito.any());
    }

    @Test
    void deveLancarExceptionAoBuscarPorId() {
        Mockito.when(repository.findById(Mockito.any())).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.consultarPorId(id));

        Assertions.assertEquals(CategoriaDataProvider.MENSAGEM_ERRO_BUSCAR_CATEGORIA_POR_ID, exception.getMessage());
    }

    @Test
    void deveDeletarCategoriaComSucesso() {
        Mockito.doNothing().when(repository).deleteById(id);

        dataProvider.deletar(id);

        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void deveLancarExceptionAoDeletarCategoria() {
        Mockito.doThrow(RuntimeException.class).when(repository).deleteById(id);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.deletar(id));

        Assertions.assertEquals(CategoriaDataProvider.MENSAGEM_ERRO_DELETAR_CATEGORIA, exception.getMessage());
    }
}
