package com.whatsapp.financeiro.entrypoint.controller;

import com.whatsapp.financeiro.application.service.UsuarioService;
import com.whatsapp.financeiro.entrypoint.dto.ResponseDto;
import com.whatsapp.financeiro.entrypoint.dto.UsuarioDto;
import com.whatsapp.financeiro.entrypoint.mapper.UsuarioMapperEntry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<ResponseDto<UsuarioDto>> cadastrar(@RequestBody @Valid UsuarioDto novoUsuario) {
        UsuarioDto resultado = UsuarioMapperEntry.paraDto(service.cadastrar(UsuarioMapperEntry.paraDomain(novoUsuario)));
        ResponseDto<UsuarioDto> response = new ResponseDto<>(resultado);

        return ResponseEntity.created(UriComponentsBuilder
                .newInstance()
                .path("/usuarios/{id}")
                .buildAndExpand(resultado.getId())
                .toUri()
        ).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UsuarioDto>> consultarPorId(@PathVariable("id") UUID idUsuario) {
        UsuarioDto resultado = UsuarioMapperEntry.paraDto(service.consultarPorId(idUsuario));
        ResponseDto<UsuarioDto> response = new ResponseDto<>(resultado);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<UsuarioDto>> alterar(@PathVariable("id") UUID idUsuario, @RequestBody UsuarioDto novosDados) {
        UsuarioDto resultado = UsuarioMapperEntry.paraDto(service.alterar(idUsuario, UsuarioMapperEntry.paraDomain(novosDados)));
        ResponseDto<UsuarioDto> response = new ResponseDto<>(resultado);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<UsuarioDto>> deletar(@PathVariable("id") UUID idUsuario) {
        service.deletar(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
