package com.whatsapp.financeiro.application.service;

import com.whatsapp.financeiro.application.exceptions.CategoriaNaoEncontradaException;
import com.whatsapp.financeiro.application.gateway.CategoriaGateway;
import com.whatsapp.financeiro.builder.CategoriaBuilder;
import com.whatsapp.financeiro.domain.Categoria;
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
    private ClienteService clienteService;

    @Captor
    private ArgumentCaptor<Categoria> captor;

    @InjectMocks
    private CategoriaService service;

    private Categoria categoriaDomainTeste;
    private Pageable pageable;
    private UUID id;

    @BeforeEach
    void inicializar() {
        categoriaDomainTeste = CategoriaBuilder.criarCategoria();

        id = categoriaDomainTeste.getId();

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void deveCriarCategoriaComSucesso() {
        Mockito.when(clienteService.buscarClientePorId(Mockito.any())).thenReturn(categoriaDomainTeste.getCliente());
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(categoriaDomainTeste);

        categoriaDomainTeste.setId(null);

        Categoria resultado = service.criarCategoria(categoriaDomainTeste);
        Categoria categoriaCapturada = captor.getValue();

        Assertions.assertEquals(categoriaCapturada.getId(), resultado.getId());
        CategoriaValidator.validaCategoriaDomain(categoriaCapturada, resultado);
    }

    @Test
    void deveBuscarTodasAsCategoriasComSucesso() {
        Page<Categoria> categoriaDomainPage = CategoriaBuilder.criarPageDeCategoria();
        Mockito.when(gateway.buscarTodas(Mockito.any())).thenReturn(categoriaDomainPage);

        Page<Categoria> resultado = service.buscarTodasCategorias(pageable);

        resultado.forEach(categoria -> CategoriaValidator.validaCategoriaDomain(categoriaDomainTeste, categoria));
    }

    @Test
    void deveBuscarCategoriaPorIdComSucesso() {
        Mockito.when(gateway.buscarPorId(Mockito.any())).thenReturn(Optional.of(categoriaDomainTeste));

        Categoria resultado = service.buscarCategoriaPorId(id);

        CategoriaValidator.validaCategoriaDomain(categoriaDomainTeste, resultado);
    }

    @Test
    void deveLancarExceptionCategoriaNaoEncontrada() {
        Mockito.when(gateway.buscarPorId(Mockito.any())).thenReturn(Optional.empty());

        CategoriaNaoEncontradaException exception = Assertions.assertThrows(
                CategoriaNaoEncontradaException.class,
                () -> service.buscarCategoriaPorId(id));

        Assertions.assertEquals(CategoriaService.ERRO_CATEGORIA_NAO_ENCONTRADA, exception.getMessage());
    }

    @Test
    void deveAlterarCategoriaComSucesso() {
        Map<String, Object> campos = Map.of("titulo", "Novo Título");

        Mockito.when(gateway.buscarPorId(Mockito.any())).thenReturn(Optional.of(categoriaDomainTeste));
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(categoriaDomainTeste);

        Categoria resultado = service.alterarCategoria(id, campos);
        Categoria categoriaCapturada = captor.getValue();

        Assertions.assertEquals("Novo Título", categoriaCapturada.getTitulo());
        CategoriaValidator.validaCategoriaDomain(categoriaCapturada, resultado);
    }

    @Test
    void deveLancarExceptionCampoNaoExistente() {
        UUID id = categoriaDomainTeste.getId();
        Map<String, Object> campos = Map.of("campoInvalido", "valor");

        Mockito.when(service.buscarCategoriaPorId(Mockito.any())).thenReturn(categoriaDomainTeste);

        AlterarCamposException exception = Assertions.assertThrows(
                AlterarCamposException.class,
                () -> service.alterarCategoria(id, campos));

        Assertions.assertEquals(CategoriaService.ERRO_ALTERAR_CAMPO + campos.get("campoInvalido"), exception.getMessage());
    }

    @Test
    void deveDeletarCategoriaComSucesso() {
        Mockito.when(service.buscarCategoriaPorId(Mockito.any())).thenReturn(categoriaDomainTeste);
        Mockito.doNothing().when(gateway).deletar(id);

        service.deletarCategoria(id);

        Mockito.verify(gateway).deletar(id);
    }
}
