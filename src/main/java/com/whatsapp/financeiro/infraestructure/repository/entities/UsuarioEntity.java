package com.whatsapp.financeiro.infraestructure.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
}
