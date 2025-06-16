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
                sa.codigoSala,
                f.pelicula.idPelicula,
                f.pelicula.nombre
            )
            FROM Funcion f
            JOIN Sala sa ON sa.id = f.sala.id
            JOIN Sede se ON se.id = sa.sede.id
            WHERE f.pelicula.idPelicula = :idPelicula
            AND se.activo = TRUE
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

    @Query("""
        SELECT new com.losagiles.CineAgile.dto.FuncionDTO(
            f.id, f.fechaHoraInicio, f.fechaHoraFin, f.dimension, f.precioBase,
            se.id, se.nombre,
            sa.id, sa.categoria, sa.codigoSala,
            f.pelicula.idPelicula,
            f.pelicula.nombre)
        FROM Funcion f
        JOIN f.pelicula p
        JOIN f.sala sa
        JOIN sa.sede se
        WHERE p.id = :idPelicula AND se.id = :idSede
        AND f.fechaHoraInicio BETWEEN :inicioSemana AND :finSemana
    """)
    List<FuncionDTO> buscarFuncionesPorSemanaConPelicula(
            @Param("idPelicula") Long idPelicula,
            @Param("idSede") Long idSede,
            @Param("inicioSemana") LocalDateTime inicioSemana,
            @Param("finSemana") LocalDateTime finSemana
    );


    @Query("""
        SELECT new com.losagiles.CineAgile.dto.FuncionDTO(
            f.id, f.fechaHoraInicio, f.fechaHoraFin, f.dimension, f.precioBase,
            se.id, se.nombre,
            sa.id, sa.categoria, sa.codigoSala,
            f.pelicula.idPelicula,
            f.pelicula.nombre)
        FROM Funcion f
        JOIN f.pelicula p
        JOIN f.sala sa
        JOIN sa.sede se
        WHERE sa.id = :idSala AND se.id = :idSede
        AND f.fechaHoraInicio BETWEEN :inicioSemana AND :finSemana
    """)
    List<FuncionDTO> buscarFuncionesPorSemanaConSala(
            @Param("idSala") Long idSala,
            @Param("idSede") Long idSede,
            @Param("inicioSemana") LocalDateTime inicioSemana,
            @Param("finSemana") LocalDateTime finSemana
    );

    @Query("""
        SELECT COUNT(f) > 0
        FROM Funcion f
        WHERE f.sala.id = :idSala
          AND f.fechaHoraInicio < :fechaHoraFin
          AND f.fechaHoraFin > :fechaHoraInicio
    """)
    boolean cruce(
        @Param("idSala") Long idSala,
        @Param("fechaHoraInicio") LocalDateTime fechaHoraInicio,
        @Param("fechaHoraFin") LocalDateTime fechaHoraFin
);



}
