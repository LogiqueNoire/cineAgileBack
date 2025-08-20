/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.repository;
import com.losagiles.CineAgile.dto.DiaHoraVentaDTO;
import com.losagiles.CineAgile.entidades.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author JOSE
 */

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    List<Entrada> findAllByFuncionIdAndButacaIdIn(Long idFuncion, Iterable<Long> ids);

    @Query("""
        SELECT COUNT(e.id.idFuncion) != 0
        FROM Entrada e
        WHERE e.id.idFuncion = :id_funcion AND e.estado = "listo"
    """)
    public boolean tieneEntradas(@Param("id_funcion") Long idFuncion);

    Optional<Entrada> findById_IdFuncionAndId_IdButaca(Long idFuncion, Long idButaca);

    @Query("""
        SELECT COALESCE(SUM(e.costoFinal))
        FROM Entrada e
        JOIN e.funcion f
        WHERE f.fechaHoraInicio BETWEEN :inicio AND :fin
        AND e.estado = 'listo'
    """)
    BigDecimal ventasEnPeriodoTiempo(@Param("inicio") LocalDateTime inicio,
                                     @Param("fin") LocalDateTime fin);

    @Query("""
        SELECT COUNT(e)
        FROM Entrada e
        JOIN e.funcion f
        WHERE f.fechaHoraInicio BETWEEN :inicio AND :fin
        AND e.estado = 'listo'
    """)
    int entradasVendidasEnPeriodoTiempo(@Param("inicio") LocalDateTime inicio,
                                        @Param("fin") LocalDateTime fin);

    @Query(value = """
        SELECT
            CAST(extract (day from f.fecha_hora_inicio) AS int),
            CAST(extract (hour from f.fecha_hora_inicio) AS int),
            COALESCE(SUM(e.costo_final))
        FROM "cine-dev".entrada e
        JOIN "cine-dev".funcion f
        ON e.id_funcion = f.id
        JOIN "cine-dev".pelicula p
        ON f.id_pelicula = p.id_pelicula
        WHERE f.fecha_hora_inicio BETWEEN :inicioSem AND :finSem
              AND e.estado = 'listo'
              AND p.id_pelicula = :idPelicula
        GROUP BY extract (day from f.fecha_hora_inicio), extract (hour from f.fecha_hora_inicio)
        ORDER BY extract (day from f.fecha_hora_inicio), extract (hour from f.fecha_hora_inicio)
    """, nativeQuery = true)
    List<Object[]> ventasDetalladaPelicula(@Param("inicioSem") LocalDateTime inicioSemana,
                                           @Param("finSem") LocalDateTime finSemana,
                                           @Param("idPelicula") Long idPelicula);
/*
    consulta
    SELECT extract (day from f.fecha_hora_inicio), extract (hour from f.fecha_hora_inicio), COALESCE(SUM(e.costo_final))
    FROM "cine-dev".entrada e
    JOIN "cine-dev".funcion f
    ON e.id_funcion = f.id
    WHERE f.fecha_hora_inicio BETWEEN '2025-06-20' AND '2025-06-28'
    AND e.estado = 'listo'
    group by extract (day from f.fecha_hora_inicio), extract (hour from f.fecha_hora_inicio)
    order by extract (day from f.fecha_hora_inicio), extract (hour from f.fecha_hora_inicio)
    */
}