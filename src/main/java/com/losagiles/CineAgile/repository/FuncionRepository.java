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
            AND sa.activo = TRUE
            """)
    public List<FuncionDTO> getFuncionesByPeliculaId(@Param("idPelicula") Long idPelicula);

    @Query("""
            SELECT
                new com.losagiles.CineAgile.dto.ButacaFuncionDTO(but, ent IS NOT NULL AND (ent.tiempoRegistro > :cincoMinAntes OR ent.estado = 'listo'))
            FROM Funcion f
            JOIN Butaca but ON f.sala.id = but.sala.id
            LEFT JOIN Entrada ent ON ent.funcion.id = f.id AND ent.butaca.id = but.id
            WHERE f.id = :idFuncion AND but.activo = TRUE
            """)
    public List<ButacaFuncionDTO> getButacaCompuestoByFuncionId(@Param("idFuncion") Long idFuncion, @Param("cincoMinAntes") LocalDateTime cincoMinAntes);

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
          AND f.id <> :idFuncion
    """)
    boolean cruceConIdFuncion(
        @Param("idSala") Long idSala,
        @Param("fechaHoraInicio") LocalDateTime fechaHoraInicio,
        @Param("fechaHoraFin") LocalDateTime fechaHoraFin,
        @Param("idFuncion") Long idFuncion
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

    @Query("""
        SELECT COUNT(f.id)
        FROM Funcion f
        WHERE f.fechaHoraInicio BETWEEN :inicio AND :fin
    """)
    int funcionesPorProyectarEnPeriodoTiempo(@Param("inicio") LocalDateTime inicio,
                                             @Param("fin") LocalDateTime fin);

    @Query("""
        SELECT COUNT(f)
        FROM Funcion f
        WHERE f.fechaHoraInicio BETWEEN :inicio AND :fin
        AND (
            SELECT COUNT(e)
            FROM Entrada e
            WHERE e.funcion = f
              AND e.estado = 'listo'
        ) = (
            SELECT COUNT(b)
            FROM Butaca b
            WHERE b.sala = f.sala
              AND b.activo = true
        )
    """)
    int funcionesAgotadasEnPeriodoTiempo(@Param("inicio") LocalDateTime inicio,
                                         @Param("fin") LocalDateTime fin);

    /* Auditar
    //Funciones con entradas en un dia
    SELECT f.id, count(e.tiempo_registro)
    FROM "cine-dev".entrada e
    JOIN "cine-dev".funcion f
    ON e.id_funcion = f.id
    WHERE f.fecha_hora_inicio BETWEEN '2025-08-17 00:00:00' AND '2025-08-18 00:00:00'
    AND e.estado = 'listo'
    group by f.id

    //Funciones con butacas por vender en un dia
    SELECT f.id, count(e.tiempo_registro)
    FROM "cine-dev".entrada e
    JOIN "cine-dev".funcion f
    ON e.id_funcion = f.id
    WHERE f.fecha_hora_inicio BETWEEN '2025-08-17 00:00:00' AND '2025-08-18 00:00:00'
    AND e.estado = 'listo'
    group by f.id
    having count(e.tiempo_registro) =
    (SELECT count(b.id)
    FROM "cine-dev".butaca b
    JOIN "cine-dev".sala s
    ON b.id_sala = s.id
    JOIN "cine-dev".funcion f2
    ON f2.id_sala = s.id
    WHERE f2.fecha_hora_inicio BETWEEN '2025-08-17 00:00:00' AND '2025-08-18 00:00:00'
    AND b.activo = true
    and f.id = f2.id)
    * */
}
