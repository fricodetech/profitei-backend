package com.whatsapp.financeiro.infraestructure.repository;

import com.whatsapp.financeiro.infraestructure.repository.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findByTelefone(String telefone);
}
