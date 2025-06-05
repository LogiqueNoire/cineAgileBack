/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USUARIO
 */

package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.dto.ButacaFuncionDTO;
import com.losagiles.CineAgile.dto.FuncionDTO;
import com.losagiles.CineAgile.entidades.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FuncionRepository extends JpaRepository<Funcion, Long>{
    @Query("""
            SELECT new com.losagiles.CineAgile.dto.FuncionDTO(
                f.id,
                f.fechaHoraInicio,
                f.fechaHoraFin,
                f.dimension,
                f.precioBase,
                se.id,
                se.nombre,
                sa.id,
                sa.categoria,
                sa.codigoSala
            )
            FROM Funcion f
            JOIN Sala sa ON sa.id = f.sala.id
            JOIN Sede se ON se.id = sa.sede.id
            WHERE f.pelicula.idPelicula = :idPelicula
            """)
    public List<FuncionDTO> getFuncionesByPeliculaId(@Param("idPelicula") Long idPelicula);

    @Query("""
            SELECT
                new com.losagiles.CineAgile.dto.ButacaFuncionDTO(but, ent IS NOT NULL)
            FROM Funcion f
            JOIN Butaca but ON f.sala.id = but.sala.id
            LEFT JOIN Entrada ent ON but.id = ent.butaca.id
            WHERE f.id = :idFuncion
            """)
    public List<ButacaFuncionDTO> getButacaCompuestoByFuncionId(@Param("idFuncion") Long idFuncion);

    @Query(value = """
    SELECT f.id, f.dimension, f.fecha_hora_inicio, f.fecha_hora_fin, sa.categoria, p.id_pelicula
    FROM pelicula AS p
    JOIN funcion AS f ON f.id_pelicula = p.id_pelicula
    JOIN sala AS sa ON f.id_sala = sa.id
    JOIN sede AS se ON sa.id_sede = se.id
    WHERE DATE(f.fecha_hora_inicio) BETWEEN 
          DATE(date_trunc('week', :fechaBase))
      AND DATE(date_trunc('week', :fechaBase)) + INTERVAL '6 days'
    """, nativeQuery = true)
    List<FuncionDTO> buscarFuncionesPorSemanaConFecha(@Param("fechaBase") LocalDateTime fechaBase);

}
