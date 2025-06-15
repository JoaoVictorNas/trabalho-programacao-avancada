package com.example.Trabalho.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FiltroAutenticacaoJwt extends OncePerRequestFilter {

    private final TokenGenerator geradorToken;
    private final DetalhesUsuarioService detalhesUsuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest requisicao, HttpServletResponse resposta, FilterChain cadeiaFiltros) throws ServletException, IOException {

        String cabecalhoAutenticacao = requisicao.getHeader("Authorization");

        if (cabecalhoAutenticacao != null && cabecalhoAutenticacao.startsWith("Bearer ")) {
            String tokenJwt = cabecalhoAutenticacao.replace("Bearer ", "");
            String emailUsuario = geradorToken.extrairEmail(tokenJwt);

            if (emailUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails detalhesUsuario = detalhesUsuarioService.loadUserByUsername(emailUsuario);

                if (geradorToken.tokenValido(tokenJwt, detalhesUsuario)) {
                    UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(detalhesUsuario, null, detalhesUsuario.getAuthorities());

                    autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(requisicao));
                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                }
            }
        }

        cadeiaFiltros.doFilter(requisicao, resposta);
    }
}
