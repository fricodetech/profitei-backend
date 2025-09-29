package com.whatsapp.financeiro.entrypoint.controller;

import com.whatsapp.financeiro.application.service.LembreteService;
import com.whatsapp.financeiro.entrypoint.dto.LembreteDto;
import com.whatsapp.financeiro.entrypoint.dto.ResponseDto;
import com.whatsapp.financeiro.entrypoint.mapper.LembreteMapperEntry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("lembretes")
@RequiredArgsConstructor
public class LembreteController {

    private final LembreteService service;

    @PostMapping
    public ResponseEntity<ResponseDto<LembreteDto>> criar(@RequestBody @Valid LembreteDto novoLembrete) {
        LembreteDto resultado = LembreteMapperEntry.paraDto(service.criar(LembreteMapperEntry.paraDomain(novoLembrete)));
        ResponseDto<LembreteDto> response = new ResponseDto<>(resultado);
        return ResponseEntity.created(
                UriComponentsBuilder
                        .newInstance()
                        .path("/lembretes/{id}")
                        .buildAndExpand(resultado.getId())
                        .toUri()
        ).body(response);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<ResponseDto<List<LembreteDto>>> listarTodos(@PathVariable("idUsuario") UUID idUsuario) {
        List<LembreteDto> resultado = service.listarTodos(idUsuario).stream().map(LembreteMapperEntry::paraDto).toList();
        ResponseDto<List<LembreteDto>> response = new ResponseDto<>(resultado);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<LembreteDto>> alterar(@RequestBody @Valid LembreteDto novosDados, @PathVariable("id") UUID idLembrete) {
        LembreteDto resultado = LembreteMapperEntry.paraDto(service.alterar(LembreteMapperEntry.paraDomain(novosDados), idLembrete));
        ResponseDto<LembreteDto> response = new ResponseDto<>(resultado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") UUID idLembrete) {
        service.deletar(idLembrete);
        return ResponseEntity.noContent().build();
    }



}
