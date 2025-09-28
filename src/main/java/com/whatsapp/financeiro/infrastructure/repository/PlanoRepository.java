package com.whatsapp.financeiro.infrastructure.repository;

import com.whatsapp.financeiro.infrastructure.repository.entities.PlanoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlanoRepository extends JpaRepository<PlanoEntity, UUID> {
}
