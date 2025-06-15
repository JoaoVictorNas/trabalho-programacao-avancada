package com.example.Atividade.controller.dto;

import com.example.Atividade.domain.Role;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UsuarioDto {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Role role;
}
