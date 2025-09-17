package com.whatsapp.financeiro.infrastructure.repository;

import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, UUID> {
}
