package com.example.Trabalho.mapper;

import com.example.Trabalho.controller.dto.UsuarioDto;
import com.example.Trabalho.domain.Usuario;
import com.example.Trabalho.infrastructure.entity.UsuarioEntity;

public class UsuarioMapper {

    public static Usuario converterDtoParaDomain(UsuarioDto usuarioDto) {
        return Usuario.builder()
                .id(usuarioDto.getId())
                .email(usuarioDto.getEmail())
                .nome(usuarioDto.getNome())
                .senha(usuarioDto.getSenha())
                .role(usuarioDto.getRole())
                .build();
    }

    public static UsuarioDto converterDomainParaDto(Usuario usuarioDomain) {
        return UsuarioDto.builder()
                .id(usuarioDomain.getId())
                .email(usuarioDomain.getEmail())
                .nome(usuarioDomain.getNome())
                .senha(usuarioDomain.getSenha())
                .role(usuarioDomain.getRole())
                .build();
    }

    public static UsuarioEntity converterDomainParaEntity(Usuario usuarioDomain) {
        return UsuarioEntity.builder()
                .id(usuarioDomain.getId())
                .email(usuarioDomain.getEmail())
                .nome(usuarioDomain.getNome())
                .senha(usuarioDomain.getSenha())
                .role(usuarioDomain.getRole())
                .build();
    }

    public static Usuario converterEntityParaDomain(UsuarioEntity usuarioEntity) {
        return Usuario.builder()
                .id(usuarioEntity.getId())
                .email(usuarioEntity.getEmail())
                .nome(usuarioEntity.getNome())
                .senha(usuarioEntity.getSenha())
                .role(usuarioEntity.getRole())
                .build();
    }
}
