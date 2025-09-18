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

    @Mock
    private CategoriaMapperInfra mapper;

    @InjectMocks
    private CategoriaDataProvider dataProvider;

    private Categoria categoriaDomainTeste;
    private CategoriaEntity categoriaEntityTeste;
    private Pageable pageable;
    private UUID id;

    @BeforeEach
    void inicializar() {
        categoriaDomainTeste = CategoriaBuilder.criarCategoria();
        categoriaEntityTeste = CategoriaBuilder.criarCategoriaEntity();

        pageable = PageRequest.of(0,10);

        id = categoriaDomainTeste.getId();
    }

    @Test
    void deveSalvarCategoriaComSucesso() {
        categoriaDomainTeste.setId(null);

        Mockito.when(repository.save(Mockito.any())).thenReturn(categoriaEntityTeste);
        Mockito.when(mapper.paraEntity(Mockito.any())).thenReturn(categoriaEntityTeste);
        Mockito.when(mapper.paraDomain(Mockito.any())).thenReturn(categoriaDomainTeste);

        Categoria categoriaResultado = dataProvider.salvar(categoriaDomainTeste);

        Assertions.assertNotNull(categoriaResultado.getId());
        CategoriaValidator.validaCategoriaDomain(categoriaDomainTeste, categoriaResultado);
    }

    @Test
    void deveLancarExceptionAoSalvar() {
        Mockito.when(repository.save(Mockito.any())).thenThrow(RuntimeException.class);
        Mockito.when(mapper.paraEntity(Mockito.any())).thenReturn(categoriaEntityTeste);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.salvar(categoriaDomainTeste));

        Assertions.assertEquals(CategoriaDataProvider.MENSAGEM_ERRO_SALVAR_CATEGORIA, exception.getMessage());
    }

    @Test
    void deveBuscarTodasCategoriasComSucesso() {
        Page<Categoria> categoriaDomainPage = CategoriaBuilder.criarPageDeCategoria();
        Page<CategoriaEntity> categoriaEntityPage = CategoriaBuilder.criarPageDeCategoriaEntity();

        Mockito.when(repository.findAll(pageable)).thenReturn(categoriaEntityPage);
        Mockito.when(mapper.paraDomain(Mockito.any())).thenReturn(categoriaDomainTeste);

        Page<Categoria> resultado = dataProvider.buscarTodas(pageable);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(categoriaDomainPage.getTotalElements(), resultado.getTotalElements());
        resultado.forEach(categoria -> CategoriaValidator.validaCategoriaDomain(categoriaDomainTeste, categoria));
    }

    @Test
    void deveLancarExceptionAoBuscarTodasCategorias() {
        Mockito.when(repository.findAll(pageable)).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.buscarTodas(pageable));

        Assertions.assertEquals(CategoriaDataProvider.MENSAGEM_ERRO_BUSCAR_CATEGORIAS, exception.getMessage());
    }

    @Test
    void deveBuscarCategoriaPorIdComSucesso() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(categoriaEntityTeste));
        Mockito.when(mapper.paraDomain(Mockito.any())).thenReturn(categoriaDomainTeste);

        Optional<Categoria> resultado = dataProvider.buscarPorId(id);

        Assertions.assertNotNull(resultado.get().getId());
        CategoriaValidator.validaCategoriaDomain(categoriaDomainTeste, resultado.get());
    }

    @Test
    void deveLancarExceptionAoBuscarPorId() {
        Mockito.when(repository.findById(Mockito.any())).thenThrow(RuntimeException.class);

        DataProviderException exception = Assertions.assertThrows(
                DataProviderException.class,
                () -> dataProvider.buscarPorId(id));

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
