package com.whatsapp.financeiro.entrypoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsapp.financeiro.builder.GastoBuilder;
import com.whatsapp.financeiro.domain.Operacao;
import com.whatsapp.financeiro.entrypoint.dto.OperacaoDto;
import com.whatsapp.financeiro.entrypoint.mapper.OperacaoMapperEntry;
import com.whatsapp.financeiro.infrastructure.mapper.OperacaoMapperInfra;
import com.whatsapp.financeiro.infrastructure.repository.OperacaoRepository;
import com.whatsapp.financeiro.infrastructure.repository.entities.OperacaoEntity;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AllArgsConstructor
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class GastoControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockitoBean
    private OperacaoRepository repository;

    private OperacaoDto gastoDto;
    private Operacao gastoDomain;
    private OperacaoEntity gastoEntity;
    private UUID idGasto;

    @BeforeEach
    void setUp() {
        gastoDto = GastoBuilder.criarGastoDto();
        gastoDomain = OperacaoMapperEntry.paraDomain(gastoDto);
        gastoEntity = OperacaoMapperInfra.paraEntity(gastoDomain);
        idGasto = gastoDto.getId();
    }

    @Test
    void deveSalvarGastoComSucesso() throws Exception {
        Mockito.when(repository.save(Mockito.any())).thenReturn(gastoEntity);

        String gastoJson = objectMapper.writeValueAsString(gastoDto);

        mockMvc.perform(post("/gastos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gastoJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/gastos/" + idGasto.toString()))
                .andExpect(jsonPath("$.dado.id").value(idGasto.toString()));

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void deveConsultarTodosGastos() throws Exception {
        Page<Operacao> page = GastoBuilder.criarPageDeGastoDomain();

        Mockito.when(repository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(page.map(OperacaoMapperInfra::paraEntity));

        mockMvc.perform(get("/gastos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dado.content[0].id").value(idGasto.toString()));

        Mockito.verify(repository).findAll(Mockito.any(Pageable.class));
    }

    @Test
    void deveConsultarGastoPorId() throws Exception {
        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(Optional.of(gastoEntity));

        mockMvc.perform(get("/gastos/{id}", idGasto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dado.id").value(idGasto.toString()));

        Mockito.verify(repository).findById(Mockito.any());
    }

    @Test
    void deveAlterarGastoComSucesso() throws Exception {
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(gastoEntity);

        gastoDto.setValor(BigDecimal.valueOf(15));

        mockMvc.perform(put("/gastos/{id}", idGasto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(gastoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dado.id").value(idGasto.toString()))
                .andExpect(jsonPath("$.dado.valor").value(BigDecimal.valueOf(15)));

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void deveDeletarGastoComSucesso() throws Exception {
        Mockito.doNothing().when(repository).deleteById(idGasto);

        mockMvc.perform(delete("/gastos/{id}", idGasto))
                .andExpect(status().isNoContent());

        Mockito.verify(repository).deleteById(idGasto);
    }
}
