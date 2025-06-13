package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.dto.UsuarioTablaDTO;
import com.losagiles.CineAgile.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    @Query("""
            SELECT new com.losagiles.CineAgile.dto.UsuarioTablaDTO(us.username, sede.nombre)
            FROM Usuario us
            LEFT JOIN Sede sede
                ON us.sede.id = sede.id
            """)
    List<UsuarioTablaDTO> findAllUsuarioTablaDTO();
}
