package com.example.Trabalho.infrastructure.entity;

import com.example.Trabalho.domain.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class EntidadeUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    private String nomeCompleto;

    @Column(unique = true)
    private String emailUsuario;

    private String senhaCriptografada;

    @Enumerated(EnumType.STRING)
    private Role papel;
}
