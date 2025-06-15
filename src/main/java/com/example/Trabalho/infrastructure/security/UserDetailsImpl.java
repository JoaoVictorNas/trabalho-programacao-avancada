package com.example.Trabalho.infrastructure.security;

import com.example.Trabalho.infrastructure.entity.UsuarioEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UsuarioUserDetails implements UserDetails {

    private final String email;
    private final String senha;
    private final Collection<? extends GrantedAuthority> permissoes;

    public UsuarioUserDetails(UsuarioEntity usuarioEntity) {
        this.email = usuarioEntity.getEmail();
        this.senha = usuarioEntity.getSenha();
        this.permissoes = List.of(new SimpleGrantedAuthority("ROLE_" + usuarioEntity.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissoes;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
