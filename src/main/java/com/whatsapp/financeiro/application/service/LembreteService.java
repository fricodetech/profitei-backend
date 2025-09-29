package com.whatsapp.financeiro.application.service;

import com.whatsapp.financeiro.application.gateway.LembreteGateway;
import com.whatsapp.financeiro.domain.Lembrete;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LembreteService {

    private final LembreteGateway gateway;

    public Lembrete criar(Lembrete paraDomain) {

    }

    public List<Lembrete> listarTodos(UUID idUsuario) {
        return gateway.listarTodos(idUsuario);
    }

    public Lembrete alterar(Lembrete novosDados, UUID idLembrete) {
        Lembrete lembrete = this.consultarPorId(idLembrete);

        lembrete.alterarAtributos(novosDados);

        return gateway.salvar(lembrete);
    }

    public void deletar(UUID idLembrete) {
        this.consultarPorId(idLembrete);
        gateway.deletar(idLembrete);
    }

    private Lembrete consultarPorId(UUID idLembrete) {
        Optional<Lembrete> lembrete = gateway.consultarPorId(idLembrete);

        if(lembrete.isEmpty()) {
            this
        }
    }

}
