package com.whatsapp.financeiro.infrastructure.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "Categoria")
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_categoria")
    private UUID id;

    private String titulo;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;
}
