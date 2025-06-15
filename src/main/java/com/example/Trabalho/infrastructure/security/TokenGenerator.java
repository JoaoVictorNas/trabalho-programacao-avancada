package com.example.Trabalho.infrastructure.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class GeradorToken {

    @Value("${jwt.secret}")
    private String segredo;

    private final long duracaoExpiracao = 86400000; // 1 dia em ms
    private Key chave;

    @PostConstruct
    public void init() {
        this.chave = Keys.hmacShaKeyFor(segredo.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(UserDetails usuarioDetalhes) {
        return Jwts.builder()
                .setSubject(usuarioDetalhes.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + duracaoExpiracao))
                .signWith(chave, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validarToken(String token, UserDetails usuarioDetalhes) {
        String email = extrairEmail(token);
        return email.equals(usuarioDetalhes.getUsername()) && !tokenExpirado(token);
    }

    public String extrairEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(chave)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean tokenExpirado(String token) {
        Date dataExpiracao = Jwts.parserBuilder()
                .setSigningKey(chave)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return dataExpiracao.before(new Date());
    }
}