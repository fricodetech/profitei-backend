package com.whatsapp.financeiro.entrypoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsapp.financeiro.builder.OperacaoBuilder;
import com.whatsapp.financeiro.domain.TipoOperacao;
import com.whatsapp.financeiro.entrypoint.dto.OperacaoDto;
import com.whatsapp.financeiro.infrastructure.mapper.OperacaoMapperInfra;
import com.whatsapp.financeiro.infrastructure.repository.CategoriaRepository;
import com.whatsapp.financeiro.infrastructure.repository.OperacaoRepository;
import com.whatsapp.financeiro.infrastructure.repository.UsuarioRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import com.whatsapp.financeiro.infrastructure.repository.entities.OperacaoEntity;
import com.whatsapp.financeiro.infrastructure.repository.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OperacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OperacaoRepository repository;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private CategoriaRepository categoriaRepository;

    private OperacaoDto operacaoDto;
    private OperacaoEntity operacaoEntity;
    private UsuarioEntity usuarioEntity;
    private CategoriaEntity categoriaEntity;

    private UUID idUsuario;
    private UUID idCategoria;

    @BeforeEach
    void setUp() {
        operacaoEntity = OperacaoBuilder.criarOperacaoEntity(TipoOperacao.GANHO);
        operacaoDto = OperacaoBuilder.criarOperacaoDto(TipoOperacao.GANHO);

        usuarioEntity = operacaoEntity.getUsuario();
        categoriaEntity = operacaoEntity.getCategoria();
    }

    @Test
    void deveSalvarOperacaoComSucesso() throws Exception {
        Mockito.when(usuarioRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(categoriaRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(categoriaEntity));
        Mockito.when(repository.save(Mockito.any())).thenReturn(operacaoEntity);

        mockMvc.perform(post("/operacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(operacaoDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/gastos/" + operacaoEntity.getId()))
                .andExpect(jsonPath("$.dado.id").value(operacaoEntity.getId().toString()));

        Mockito.verify(usuarioRepository).findById(Mockito.any(UUID.class));
        Mockito.verify(categoriaRepository).findById(Mockito.any(UUID.class));
        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void deveConsultarTodasOperacoesComSucesso() throws Exception {
        Page<OperacaoEntity> operacaoPage = OperacaoBuilder.criarPageDeOperacaoDomain().map(OperacaoMapperInfra::paraEntity);
        Mockito.when(usuarioRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(repository.findAllByUsuarioId(Mockito.any(UUID.class), Mockito.any(Pageable.class)))
                .thenReturn(operacaoPage);

        mockMvc.perform(get("/operacoes")
                        .param("idUsuario", idUsuario.toString())
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "dataOperacao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dado.totalElements").value(1));

        Mockito.verify(usuarioRepository).findById(Mockito.any(UUID.class));
        Mockito.verify(repository).findAllByUsuarioId(Mockito.any(UUID.class), Mockito.any(Pageable.class));
    }

    @Test
    void deveConsultarTodosGastosComSucesso() throws Exception {
        Page<OperacaoEntity> operacaoPage = OperacaoBuilder.criarPageDeOperacaoDomain().map(OperacaoMapperInfra::paraEntity);
        Mockito.when(usuarioRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(repository.findAllByTipoOperacaoAndUsuarioId(Mockito.eq(TipoOperacao.GASTO),
                        Mockito.any(UUID.class), Mockito.any(Pageable.class)))
                .thenReturn(operacaoPage);

        mockMvc.perform(get("/operacoes/gastos")
                        .param("idUsuario", idUsuario.toString())
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "dataOperacao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dado.totalElements").value(1));

        Mockito.verify(usuarioRepository).findById(Mockito.any(UUID.class));
        Mockito.verify(repository).findAllByTipoOperacaoAndUsuarioId(Mockito.eq(TipoOperacao.GASTO),
                Mockito.any(UUID.class), Mockito.any(Pageable.class));
    }

    @Test
    void deveDeletarOperacaoComSucesso() throws Exception {
        Mockito.when(repository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(operacaoEntity));
        Mockito.doNothing().when(repository).deleteById(Mockito.any());

        mockMvc.perform(delete("/operacoes/" + operacaoEntity.getId()))
                .andExpect(status().isNoContent());

        Mockito.verify(repository).deleteById(Mockito.any());
    }
}
