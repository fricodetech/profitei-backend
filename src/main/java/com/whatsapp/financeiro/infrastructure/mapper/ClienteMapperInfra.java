package com.whatsapp.financeiro.infrastructure.mapper;

import com.whatsapp.financeiro.domain.Cliente;
import com.whatsapp.financeiro.infrastructure.repository.entities.ClienteEntity;

public class ClienteMapperInfra {
    public static ClienteEntity paraEntity(Cliente domain) {
        return new ClienteEntity();
    }

    public static Cliente paraDomain(ClienteEntity entity) {
        return new Cliente();
    }
}
