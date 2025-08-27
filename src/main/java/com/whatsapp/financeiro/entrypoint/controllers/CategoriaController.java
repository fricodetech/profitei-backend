package com.whatsapp.financeiro.entrypoint.controllers;

import com.whatsapp.financeiro.application.services.CategoriaService;
import com.whatsapp.financeiro.entrypoint.dtos.CategoriaDto;
import com.whatsapp.financeiro.entrypoint.dtos.ResponseDto;
import com.whatsapp.financeiro.entrypoint.mappers.CategoriaMapperEntry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;
    private final CategoriaMapperEntry mapper;

    @PostMapping
    public ResponseEntity<ResponseDto<CategoriaDto>> criarCategoria(@RequestBody @Valid CategoriaDto categoriaCriada) {
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

    @GetMapping
    public ResponseEntity<ResponseDto<Page<CategoriaDto>>> buscarTodasCategorias(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titulo") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<CategoriaDto> categoriaPage = service.buscarTodasCategorias(pageable).map(mapper::paraDto);

        ResponseDto<Page<CategoriaDto>> response = new ResponseDto<>(categoriaPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<CategoriaDto>> buscarCategoriaPorId(@PathVariable UUID id) {
        CategoriaDto categoriaBuscada = mapper.paraDto(service.buscarCategoriaPorId(id));

        ResponseDto<CategoriaDto> response = new ResponseDto<>(categoriaBuscada);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<CategoriaDto>> alterarCategoria(@PathVariable UUID id, @RequestBody Map<String , Object> campos) {
        CategoriaDto categoriaAlterada = mapper.paraDto(service.alterarCategoria(id, campos));

        ResponseDto<CategoriaDto> response = new ResponseDto<>(categoriaAlterada);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable UUID id) {
        service.deletarCategoria(id);

        return ResponseEntity.noContent().build();
    }
}
