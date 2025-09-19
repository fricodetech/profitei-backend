package com.whatsapp.financeiro.entrypoint.controller;

import com.whatsapp.financeiro.application.service.GastoService;
import com.whatsapp.financeiro.entrypoint.dto.GastoDto;
import com.whatsapp.financeiro.entrypoint.dto.ResponseDto;
import com.whatsapp.financeiro.entrypoint.mapper.GastoMapperEntry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/gastos")
@RequiredArgsConstructor
public class GastoController {

    private final GastoService service;

    @PostMapping
    public ResponseEntity<ResponseDto<GastoDto>> salvarGasto(@RequestBody @Valid GastoDto gastoDto) {

        GastoDto gastoSalvo = GastoMapperEntry.paraDto(
                service.salvarGasto(GastoMapperEntry.paraDomain(gastoDto)));

        ResponseDto<GastoDto> response = new ResponseDto<>(gastoSalvo);

        return ResponseEntity.created(
                UriComponentsBuilder
                        .newInstance()
                        .path("/gastos/{id}")
                        .buildAndExpand(gastoSalvo.getId())
                        .toUri()
        ).body(response);
    }
}
