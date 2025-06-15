package com.example.Trabalho.domain;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UsuarioDominio {
    private Long identificador;
    private String nomeCompleto;
    private String emailUsuario;
    private String senhaCriptografada;
    private Role papel;

    public void atualizarInformacoes(UsuarioDominio novoUsuario){
        this.nomeCompleto = novoUsuario.getNomeCompleto();
        this.emailUsuario = novoUsuario.getEmailUsuario();
    }
}
