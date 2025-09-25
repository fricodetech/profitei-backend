package com.whatsapp.financeiro.infrastructure.repository.entities;

import com.whatsapp.financeiro.domain.TipoOperacao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "Operacao")
@Table(name = "Operacoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OperacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_gasto")
    private UUID id;

    private String descricao;

    @Enumerated(EnumType.ORDINAL)
    private TipoOperacao tipoOperacao;

    private BigDecimal valor;

    @Column(name = "data_operacao")
    private LocalDate dataOperacao;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaEntity categoria;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;
}
