package com.whatsapp.financeiro.entrypoint.controller;

import com.whatsapp.financeiro.application.service.OperacaoService;
import com.whatsapp.financeiro.entrypoint.dto.OperacaoDto;
import com.whatsapp.financeiro.entrypoint.dto.ResponseDto;
import com.whatsapp.financeiro.entrypoint.mapper.OperacaoMapperEntry;
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
@RequestMapping("/operacoes")
@RequiredArgsConstructor
public class OperacaoController {

    private final OperacaoService service;

    @PostMapping
    public ResponseEntity<ResponseDto<OperacaoDto>> salvarOperacao(@RequestBody @Valid OperacaoDto operacaoDto) {

        OperacaoDto operacaoSalvo = OperacaoMapperEntry.paraDto(
                service.salvarOperacao(OperacaoMapperEntry.paraDomain(operacaoDto)));

        ResponseDto<OperacaoDto> response = new ResponseDto<>(operacaoSalvo);

        return ResponseEntity.created(
                UriComponentsBuilder
                        .newInstance()
                        .path("/gastos/{id}")
                        .buildAndExpand(operacaoSalvo.getId())
                        .toUri()
        ).body(response);
    }

    @GetMapping()
    public ResponseEntity<ResponseDto<Page<OperacaoDto>>> consultarTodasOperacoes(
            @RequestParam UUID idUsuario,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataOperacao") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<OperacaoDto> operacoesDtoPage = service.consultarTodasOperacoes(idUsuario, pageable).map(OperacaoMapperEntry::paraDto);

        ResponseDto<Page<OperacaoDto>> response = new ResponseDto<>(operacoesDtoPage);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/gastos")
    public ResponseEntity<ResponseDto<Page<OperacaoDto>>> consultarTodosGastos(
            @RequestParam UUID idUsuario,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataOperacao") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<OperacaoDto> operacoesDtoPage = service.consultarTodosGastos(idUsuario, pageable).map(OperacaoMapperEntry::paraDto);

        ResponseDto<Page<OperacaoDto>> response = new ResponseDto<>(operacoesDtoPage);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ganhos")
    public ResponseEntity<ResponseDto<Page<OperacaoDto>>> consultarTodosGanhos(
            @RequestParam UUID idUsuario,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataOperacao") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<OperacaoDto> operacoesDtoPage = service.consultarTodosGanhos(idUsuario, pageable).map(OperacaoMapperEntry::paraDto);

        ResponseDto<Page<OperacaoDto>> response = new ResponseDto<>(operacoesDtoPage);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<OperacaoDto>> consultarOperacaoPorId(@PathVariable UUID id) {

        OperacaoDto operacaoDto = OperacaoMapperEntry.paraDto(service.consultarOperacaoPorId(id));

        ResponseDto<OperacaoDto> response = new ResponseDto<>(operacaoDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<OperacaoDto>> alterarOperacao(@PathVariable UUID id, @RequestBody OperacaoDto operacaoDtoAlterado) {

        OperacaoDto operacaoDto = OperacaoMapperEntry.paraDto(service.alterarOperacao(id, OperacaoMapperEntry.paraDomain(operacaoDtoAlterado)));

        ResponseDto<OperacaoDto> response = new ResponseDto<>(operacaoDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOperacao(@PathVariable UUID id) {

        service.deletarOperacao(id);

        return ResponseEntity.noContent().build();
    }

}
