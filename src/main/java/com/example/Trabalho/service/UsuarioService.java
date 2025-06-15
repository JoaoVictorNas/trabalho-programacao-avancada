package com.example.Trabalho.service;

import com.example.Trabalho.controller.dto.UsuarioDto;
import com.example.Trabalho.domain.Usuario;
import com.example.Trabalho.infrastructure.repository.UsuarioRepository;
import com.example.Trabalho.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UsuarioService {

    private PasswordEncoder passwordEncoder;
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario novoUsuario){
        log.info("Iniciando registro do usuário: {}", novoUsuario);

        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        Usuario usuarioSalvo = UsuarioMapper.entityParaDomain(
                usuarioRepository.save(UsuarioMapper.domainParaEntity(novoUsuario)));

        log.info("Usuário registrado com sucesso: {}", usuarioSalvo);
        return usuarioSalvo;
    }

    public List<Usuario> obterTodosUsuarios() {
        log.info("Iniciando listagem de todos os usuários com perfil USER");

        List<Usuario> listaUsuarios = usuarioRepository.findAllByRoleUser()
            .stream()
            .map(UsuarioMapper::entityParaDomain)
            .toList();

        log.info("Listagem finalizada. Total de usuários: {}", listaUsuarios.size());
        return listaUsuarios;
    }

    public Usuario atualizarUsuarioPorId(Long idUsuario, Usuario dadosAtualizados) {
        log.info("Iniciando atualização do usuário ID: {}. Novos dados: {}", idUsuario, dadosAtualizados);

        Usuario usuarioExistente = buscarUsuarioPorId(idUsuario);
        usuarioExistente.alterarDados(dadosAtualizados);

        Usuario usuarioAtualizado = UsuarioMapper.entityParaDomain(
            usuarioRepository.save(UsuarioMapper.domainParaEntity(usuarioExistente)));

        log.info("Usuário atualizado com sucesso: {}", usuarioAtualizado);
        return usuarioAtualizado;
    }

    private Usuario buscarUsuarioPorId(Long idUsuario){
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario)
            .map(UsuarioMapper::entityParaDomain);

        if (usuarioOpt.isEmpty()){
            log.error("Usuário não encontrado para o ID: {}", idUsuario);
            throw new RuntimeException("Usuário não encontrado.");
        }

        return usuarioOpt.get();
    }

    public void removerUsuarioPorId(Long idUsuario) {
        log.info("Iniciando remoção do usuário ID: {}", idUsuario);

        buscarUsuarioPorId(idUsuario);
        usuarioRepository.deleteById(idUsuario);

        log.info("Usuário removido com sucesso ID: {}", idUsuario);
    }
}
