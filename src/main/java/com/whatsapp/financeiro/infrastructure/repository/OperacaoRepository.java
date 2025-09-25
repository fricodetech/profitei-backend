package com.whatsapp.financeiro.infrastructure.repository;

import com.whatsapp.financeiro.domain.TipoOperacao;
import com.whatsapp.financeiro.infrastructure.repository.entities.OperacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OperacaoRepository extends JpaRepository<OperacaoEntity, UUID> {
    Page<OperacaoEntity> findAllByUsuarioId(
            UUID idUsuario,
            Pageable pageable);

    Page<OperacaoEntity> findAllByTipoOperacaoAndUsuarioId(
            TipoOperacao tipoOperacao,
            UUID idUsuario,
            Pageable pageable);
}
