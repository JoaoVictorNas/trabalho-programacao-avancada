package com.example.Trabalho.infrastructure.repository;

import com.example.Trabalho.infrastructure.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioUsuario extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> buscarPorEmail(String email);

    @Query("""
            SELECT u
            FROM Usuario u
            WHERE role = 'USER'
            """)
    List<UsuarioEntity> listarTodosUsuariosComPapelUser();
}
