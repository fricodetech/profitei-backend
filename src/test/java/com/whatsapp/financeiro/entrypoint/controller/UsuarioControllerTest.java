package com.whatsapp.financeiro.entrypoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsapp.financeiro.builder.UsuarioBuilder;
import com.whatsapp.financeiro.entrypoint.dto.UsuarioDto;
import com.whatsapp.financeiro.infrastructure.repository.UsuarioRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Captor
    ArgumentCaptor<UsuarioEntity> captor;

    private UsuarioDto usuarioDto;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        usuarioDto = UsuarioBuilder.builderUsuarioDto();
        usuarioEntity = UsuarioBuilder.builderUsuarioEntity();
    }

    @Test
    void deveCadastrarComSucesso() throws Exception {
        Mockito.when(usuarioRepository.findByTelefone(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(usuarioRepository.save(Mockito.any())).thenReturn(usuarioEntity);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(usuarioDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/usuarios/" + usuarioEntity.getId()))
                .andExpect(jsonPath("$.dado.id").value(usuarioEntity.getId().toString()));

        Mockito.verify(usuarioRepository).findByTelefone(Mockito.anyString());
        Mockito.verify(usuarioRepository).save(Mockito.any());
    }

    @Test
    void deveConsultarPorIdComSucesso() throws Exception {
        Mockito.when(usuarioRepository.findById(Mockito.any())).thenReturn(Optional.of(usuarioEntity));

        mockMvc.perform(get("/usuarios/" + usuarioDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dado.id").value(usuarioEntity.getId().toString()));

        Mockito.verify(usuarioRepository).findById(Mockito.any());
    }

    @Test
    void deveAlterarComSucesso() throws Exception {
        Mockito.when(usuarioRepository.findById(Mockito.any())).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(usuarioRepository.save(captor.capture())).thenReturn(usuarioEntity);

        mockMvc.perform(put("/usuarios/" + usuarioDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(usuarioDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dado.id").value(usuarioEntity.getId().toString()));

        Mockito.verify(usuarioRepository).findById(Mockito.any());
        Mockito.verify(usuarioRepository).save(Mockito.any());
    }

    @Test
    void deveDeletarComSucesso() throws Exception {
        Mockito.when(usuarioRepository.findById(Mockito.any())).thenReturn(Optional.of(usuarioEntity));
        Mockito.doNothing().when(usuarioRepository).deleteById(Mockito.any());

        mockMvc.perform(delete("/usuarios/" + usuarioDto.getId()))
                .andExpect(status().isNoContent());

        Mockito.verify(usuarioRepository).findById(Mockito.any());
        Mockito.verify(usuarioRepository).deleteById(Mockito.any());
    }
}