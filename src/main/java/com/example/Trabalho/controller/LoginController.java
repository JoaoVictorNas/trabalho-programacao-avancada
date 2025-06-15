package com.example.Atividade.controller;

import com.example.Atividade.controller.dto.LoginRequest;
import com.example.Atividade.controller.dto.LoginResponse;
import com.example.Atividade.infrastructure.security.TokenGenerator;
import com.example.Atividade.infrastructure.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenGenerator tokenGenerator;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping
    public ResponseEntity<LoginResponse> autenticar(@RequestBody LoginRequest dadosLogin) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dadosLogin.getEmail(), dadosLogin.getSenha());

        authenticationManager.authenticate(authenticationToken);

        UserDetails usuarioAutenticado = userDetailsService.loadUserByUsername(dadosLogin.getEmail());
        String tokenJwt = tokenGenerator.gerarToken(usuarioAutenticado);

        return ResponseEntity.ok(new LoginResponse(tokenJwt));
    }
}
