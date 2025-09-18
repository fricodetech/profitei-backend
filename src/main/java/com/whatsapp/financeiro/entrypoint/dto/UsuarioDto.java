package com.whatsapp.financeiro.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDto {

    @JsonProperty("id")
    private UUID id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @JsonProperty("telefone")
    private String telefone;

    @NotBlank(message = "Senha é obrigatório")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).+$",
            message = "A senha deve conter pelo menos uma letra, um número e um caractere especial.")
    @JsonProperty("senha")
    private String senha;

    @NotBlank(message = "Plano é obrigatório")
    private PlanoDto plano;
}
