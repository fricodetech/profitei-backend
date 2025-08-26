package com.whatsapp.financeiro.entrypoint.controllers;

import com.whatsapp.financeiro.application.services.CategoriaService;
import com.whatsapp.financeiro.entrypoint.dtos.CategoriaDto;
import com.whatsapp.financeiro.entrypoint.dtos.ResponseDto;
import com.whatsapp.financeiro.entrypoint.mappers.CategoriaMapperEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;
    private final CategoriaMapperEntry mapper;

    @PostMapping
    public ResponseEntity<ResponseDto<CategoriaDto>> criarCategoria(@RequestBody CategoriaDto categoriaCriada) {

        CategoriaDto categoriaSalva = mapper.paraDto(service.criarCategoria(mapper.paraDomain(categoriaCriada)));

        ResponseDto<CategoriaDto> response = new ResponseDto<>(categoriaSalva);
        return ResponseEntity.created(
                UriComponentsBuilder
                        .newInstance()
                        .path("/categorias/{id}")
                        .buildAndExpand(categoriaSalva.getId())
                        .toUri()
        ).body(response);
    }
}
