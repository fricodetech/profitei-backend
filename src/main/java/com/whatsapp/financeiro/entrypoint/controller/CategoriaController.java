package com.whatsapp.financeiro.entrypoint.controller;

import com.whatsapp.financeiro.application.service.CategoriaService;
import com.whatsapp.financeiro.domain.Categoria;
import com.whatsapp.financeiro.entrypoint.dto.CategoriaDto;
import com.whatsapp.financeiro.entrypoint.dto.ResponseDto;
import com.whatsapp.financeiro.entrypoint.mapper.CategoriaMapperEntry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @PostMapping
    public ResponseEntity<ResponseDto<CategoriaDto>> criarCategoria(@RequestBody @Valid CategoriaDto categoriaCriada) {

        CategoriaDto categoriaSalva = CategoriaMapperEntry.paraDto(
                service.criarCategoria(CategoriaMapperEntry.paraDomain(categoriaCriada)));

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
    public ResponseEntity<ResponseDto<Page<CategoriaDto>>> consultarTodasCategorias(
            @RequestParam UUID idUsuario,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titulo") String sort
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<CategoriaDto> categoriaPage = service.consultarTodasCategorias(idUsuario, pageable).map(CategoriaMapperEntry::paraDto);

        ResponseDto<Page<CategoriaDto>> response = new ResponseDto<>(categoriaPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<CategoriaDto>> consultarCategoriaPorId(@PathVariable UUID id) {

        CategoriaDto categoriaBuscada = CategoriaMapperEntry.paraDto(service.consultarCategoriaPorId(id));

        ResponseDto<CategoriaDto> response = new ResponseDto<>(categoriaBuscada);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<CategoriaDto>> alterarCategoria(@PathVariable UUID id, @RequestBody Categoria categoriaNova) {

        CategoriaDto categoriaAlterada = CategoriaMapperEntry.paraDto(service.alterarCategoria(id, categoriaNova));

        ResponseDto<CategoriaDto> response = new ResponseDto<>(categoriaAlterada);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable UUID id) {

        service.deletarCategoria(id);

        return ResponseEntity.noContent().build();
    }
}
