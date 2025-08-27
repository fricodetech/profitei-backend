package com.whatsapp.financeiro.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Cliente {
    private UUID id;
}
