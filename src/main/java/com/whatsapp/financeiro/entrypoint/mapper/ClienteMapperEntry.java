package com.whatsapp.financeiro.entrypoint.mapper;

import com.whatsapp.financeiro.domain.Cliente;
import com.whatsapp.financeiro.entrypoint.dto.ClienteDto;

public class ClienteMapperEntry {
    public static Cliente paraDomain(ClienteDto dto) {
        return new Cliente();
    }

    public static ClienteDto paraDto(Cliente domain) {
        return new ClienteDto();
    }
}
