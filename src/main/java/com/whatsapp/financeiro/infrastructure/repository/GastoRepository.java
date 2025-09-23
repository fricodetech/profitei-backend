package com.whatsapp.financeiro.infrastructure.repository;

import com.whatsapp.financeiro.infrastructure.repository.entities.GastoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GastoRepository extends JpaRepository<GastoEntity, UUID> {
}
