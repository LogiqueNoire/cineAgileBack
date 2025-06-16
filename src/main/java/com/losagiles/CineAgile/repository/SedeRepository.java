/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author mvela
 */

package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.dto.NombreDTO;
import com.losagiles.CineAgile.dto.PeliculaCarteleraDTO;
import com.losagiles.CineAgile.entidades.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {
    @Query("""
            SELECT new com.losagiles.CineAgile.dto.NombreDTO(
                    s.id,
                    s.nombre,
                    s.activo
            )
            FROM Sede s
            """)
    public List<NombreDTO> getNombresSedes();

    @Query("""
            SELECT new com.losagiles.CineAgile.dto.NombreDTO(
                    s.id,
                    s.nombre
            )
            FROM Sede s
            where s.activo = true
            """)
    public List<NombreDTO> getNombresSedesActivas();

    public boolean existsByNombre(String nombre);

    public List<Sede> findByNombre(String nombre);

}
