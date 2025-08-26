package com.whatsapp.financeiro.infrastructure.repositories;

import com.whatsapp.financeiro.infrastructure.repositories.entities.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, UUID> {
}
