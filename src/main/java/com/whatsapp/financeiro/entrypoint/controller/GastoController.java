package com.whatsapp.financeiro.entrypoint.controller;

import com.whatsapp.financeiro.application.service.GastoService;
import com.whatsapp.financeiro.entrypoint.dto.GastoDto;
import com.whatsapp.financeiro.entrypoint.dto.ResponseDto;
import com.whatsapp.financeiro.entrypoint.mapper.GastoMapperEntry;
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

    @GetMapping()
    public ResponseEntity<ResponseDto<Page<GastoDto>>> consultarTodosGastos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titulo") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<GastoDto> gastoDtoPage = service.consultarTodosGastos(pageable).map(GastoMapperEntry::paraDto);

        ResponseDto<Page<GastoDto>> response = new ResponseDto<>(gastoDtoPage);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<GastoDto>> consultarGastoPorId(@PathVariable UUID id) {

        GastoDto gastoDto = GastoMapperEntry.paraDto(service.consultarGastoPorId(id));

        ResponseDto<GastoDto> response = new ResponseDto<>(gastoDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<GastoDto>> alterarGasto(@PathVariable UUID id, @RequestBody GastoDto gastoDtoAlterado) {

        GastoDto gastoDto = GastoMapperEntry.paraDto(service.alterarGasto(id, GastoMapperEntry.paraDomain(gastoDtoAlterado)));

        ResponseDto<GastoDto> response = new ResponseDto<>(gastoDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarGasto(@PathVariable UUID id) {

        service.deletarGasto(id);

        return ResponseEntity.noContent().build();
    }

}
