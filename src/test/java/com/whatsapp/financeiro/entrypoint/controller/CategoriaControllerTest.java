package com.whatsapp.financeiro.entrypoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsapp.financeiro.builder.CategoriaBuilder;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.entrypoint.dto.CategoriaDto;
import com.whatsapp.financeiro.infrastructure.mapper.CategoriaMapperInfra;
import com.whatsapp.financeiro.infrastructure.mapper.UsuarioMapperInfra;
import com.whatsapp.financeiro.infrastructure.repository.CategoriaRepository;
import com.whatsapp.financeiro.infrastructure.repository.UsuarioRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import com.whatsapp.financeiro.infrastructure.repository.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoriaRepository repository;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    private CategoriaDto categoriaDto;
    private CategoriaEntity categoriaEntity;
    private UsuarioEntity usuarioEntity;

    private UUID idUsuario;

    @BeforeEach
    void setUp() {
        categoriaEntity = CategoriaBuilder.criarCategoriaEntity();
        categoriaDto = CategoriaBuilder.criarCategoriaDto();

        usuarioEntity = categoriaEntity.getUsuario();
    }

    @Test
    void deveCriarCategoriaComSucesso() throws Exception {
        Mockito.when(usuarioRepository.findById(Mockito.any())).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(repository.save(Mockito.any())).thenReturn(categoriaEntity);

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(categoriaDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/categorias/" + categoriaEntity.getId()))
                .andExpect(jsonPath("$.dado.id").value(categoriaEntity.getId().toString()));

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void deveConsultarTodasCategoriasComSucesso() throws Exception {
        Page<Categoria> categoriaPage = CategoriaBuilder.criarPageDeCategoriaDomain();

        Mockito.when(usuarioRepository.findById(Mockito.any())).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(repository.findAllByUsuarioId(Mockito.any(UUID.class), Mockito.any(Pageable.class)))
                .thenReturn(categoriaPage.map(CategoriaMapperInfra::paraEntity));

        mockMvc.perform(get("/categorias")
                        .param("idUsuario", idUsuario.toString())
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "titulo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dado.totalElements").value(1));

        Mockito.verify(repository).findAllByUsuarioId(Mockito.any(UUID.class), Mockito.any(Pageable.class));
    }

    @Test
    void deveConsultarCategoriaPorIdComSucesso() throws Exception {
        Mockito.when(repository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(categoriaEntity));

        mockMvc.perform(get("/categorias/" + categoriaDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dado.id").value(categoriaEntity.getId().toString()));

        Mockito.verify(repository).findById(Mockito.any(UUID.class));
    }

    @Test
    void deveAlterarCategoriaComSucesso() throws Exception {
        Categoria categoriaAlterada = CategoriaBuilder.criarCategoriaDomain();
        categoriaAlterada.setTitulo("Novo título");
        categoriaAlterada.setDescricao("Nova descrição");

        Mockito.when(repository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(categoriaEntity));
        Mockito.when(repository.save(Mockito.any())).thenReturn(CategoriaMapperInfra.paraEntity(categoriaAlterada));

        mockMvc.perform(put("/categorias/" + categoriaDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(categoriaAlterada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dado.titulo").value("Novo título"))
                .andExpect(jsonPath("$.dado.descricao").value("Nova descrição"));

        Mockito.verify(repository).findById(Mockito.any(UUID.class));
        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void deveDeletarCategoriaComSucesso() throws Exception {
        Mockito.when(repository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(categoriaEntity));
        Mockito.doNothing().when(repository).deleteById(Mockito.any(UUID.class));

        mockMvc.perform(delete("/categorias/" + categoriaDto.getId()))
                .andExpect(status().isNoContent());

        Mockito.verify(repository).deleteById(Mockito.any());
    }
}
