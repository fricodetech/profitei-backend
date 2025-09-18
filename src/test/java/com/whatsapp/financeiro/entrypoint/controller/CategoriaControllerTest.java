package com.whatsapp.financeiro.entrypoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsapp.financeiro.builder.CategoriaBuilder;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.entrypoint.dto.CategoriaDto;
import com.whatsapp.financeiro.entrypoint.mapper.CategoriaMapperEntry;
import com.whatsapp.financeiro.infrastructure.mapper.CategoriaMapperInfra;
import com.whatsapp.financeiro.infrastructure.repository.CategoriaRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import com.whatsapp.financeiro.validators.json.CategoriaValidatorJson;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AllArgsConstructor
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoriaControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoriaRepository repository;

    @MockitoBean
    private ClienteRepository clienteRepository;

    @MockitoBean
    private PlanoRepository planoRepository;

    private CategoriaDto categoriaDtoEntrada;
    private Categoria categoriaDomain;
    private CategoriaEntity categoriaEntity;

    @BeforeEach
    void inicializarAtributos() {
        categoriaDtoEntrada = CategoriaBuilder.criarCategoriaDto();
        categoriaDomain = CategoriaMapperEntry.paraDomain(categoriaDtoEntrada);
        categoriaEntity = CategoriaMapperInfra.paraEntity(categoriaDomain);
    }

    @Test
    void testeCriarCategoria() throws Exception {
        categoriaDtoEntrada.setId(null);

        Mockito.when(clienteRepository.findById(Mockito.any())).thenReturn(Optional.of(categoriaEntity.getCliente()));
        Mockito.when(planoRepository.findById(Mockito.any())).thenReturn(Optional.of(categoriaEntity.getCliente().getPlano()));
        Mockito.when(repository.save(Mockito.any())).thenReturn(categoriaEntity);

        String categoriaJson = objectMapper.writeValueAsString(categoriaDtoEntrada);
        ResultActions resultado = mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoriaJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/categorias/" + categoriaDomain.getId().toString()));

        CategoriaValidatorJson.validaCategoriaJson(resultado, CategoriaMapperEntry.paraDto(categoriaDomain));

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void testeBuscarCategoriaPorId() throws Exception {
        UUID id = categoriaDtoEntrada.getId();

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(categoriaEntity));

        ResultActions resultado = mockMvc.perform(get("/categorias/{id}", id))
                .andExpect(status().isOk());

        CategoriaValidatorJson.validaCategoriaJson(resultado, categoriaDtoEntrada);

        Mockito.verify(repository).findById(Mockito.any());
    }

    @Test
    void testeBuscarTodasCategorias() throws Exception {
        Page<CategoriaEntity> categoriaPage = CategoriaBuilder.criarPageDeCategoriaEntity();

        Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(categoriaPage);

        ResultActions resultado = mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk());

        CategoriaValidatorJson.validaPageResponseCategoria(resultado, (int) categoriaPage.getTotalElements());

        Mockito.verify(repository).findAll(Mockito.any(Pageable.class));
    }

    @Test
    void testeAlterarCategoria() throws Exception {
        UUID id = categoriaDomain.getId();
        Map<String, Object> campos = Map.of("titulo", "Novo título");

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(categoriaEntity));

        categoriaEntity.setTitulo(campos.get("titulo").toString());
        Mockito.when(repository.save(Mockito.any())).thenReturn(categoriaEntity);

        String camposJson = objectMapper.writeValueAsString(campos);
        ResultActions resultado = mockMvc.perform(patch("/categorias/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(camposJson))
                .andExpect(status().isOk());

        categoriaDomain.setTitulo(campos.get("titulo").toString());

        CategoriaValidatorJson.validaCategoriaJson(resultado, CategoriaMapperEntry.paraDto(categoriaDomain));

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void testeDeletarCategoria() throws Exception {
        UUID id = categoriaDomain.getId();

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(categoriaEntity));
        Mockito.doNothing().when(repository).deleteById(Mockito.any());

        ResultActions resultado = mockMvc.perform(delete("/categorias/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }
}
