package com.example.Atividade.controller;

import com.example.Atividade.controller.dto.UsuarioDto;
import com.example.Atividade.mapper.UsuarioMapper;
import com.example.Atividade.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> criarUsuario(@RequestBody UsuarioDto novoUsuarioDto) {
        UsuarioDto usuarioCriadoDto = UsuarioMapper.domainParaDto(
                usuarioService.cadastrar(UsuarioMapper.dtoParaDomain(novoUsuarioDto)));

        return ResponseEntity.created(
                UriComponentsBuilder
                        .newInstance()
                        .path("/usuarios/{id}")
                        .buildAndExpand(usuarioCriadoDto.getId())
                        .toUri()
        ).body(usuarioCriadoDto);
    }

    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDto>> obterTodosUsuarios() {
        List<UsuarioDto> listaUsuariosDto = usuarioService.listarTodos()
                .stream()
                .map(UsuarioMapper::domainParaDto)
                .toList();

        return ResponseEntity.ok(listaUsuariosDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UsuarioDto> modificarUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuarioAtualizarDto) {
        UsuarioDto usuarioAtualizadoDto = UsuarioMapper.domainParaDto(
                usuarioService.atualizar(id, UsuarioMapper.dtoParaDomain(usuarioAtualizarDto)));

        return ResponseEntity.ok(usuarioAtualizadoDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}