package com.whatsapp.financeiro.infrastructure.repository;

import com.whatsapp.financeiro.infrastructure.repository.entities.CategoriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, UUID> {
    Page<CategoriaEntity> findAllByUsuarioId(UUID id, Pageable pageable);
}
