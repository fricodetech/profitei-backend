package com.whatsapp.financeiro.builder;

import com.whatsapp.financeiro.domain.Plano;
import com.whatsapp.financeiro.entrypoint.dto.PlanoDto;
import com.whatsapp.financeiro.infrastructure.repository.entities.PlanoEntity;

public class PlanoBuilder {

    public static PlanoDto criarPlanoDto() {
        return PlanoDto.builder().build();
    }

    public static Plano criarPlanoDomain() {
        return Plano.builder().build();
    }



    public static PlanoEntity criarPlanoEntity() {
        return PlanoEntity.builder().build();
    }
}
