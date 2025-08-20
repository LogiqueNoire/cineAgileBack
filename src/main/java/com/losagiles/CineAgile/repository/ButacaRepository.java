/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author mvela
 */

package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.entidades.Butaca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ButacaRepository extends JpaRepository<Butaca, Long> {
    public List<Butaca> findAllBySalaId(Long idSala);

    @Query ("""
            SELECT COUNT(b.id)
            FROM Butaca b
            LEFT JOIN Entrada e
            ON b.id = e.id.idButaca
            AND e.id.idFuncion = :funcionid
            WHERE b.activo = TRUE
            AND b.sala.id = (
            	SELECT sala.id FROM Funcion f WHERE f.id = :funcionid
            )
            AND (e IS null OR (e.estado != 'listo' AND e.tiempoRegistro <= :tiempoCincoMinAntes))
            """)
    public int consultarCantidadButacasDisponibles(@Param("funcionid") Long idFuncion,
                                                   @Param("tiempoCincoMinAntes") LocalDateTime tiempoCincoMinAntes);

    /*
    @Query ("""
            SELECT count(b.id) as butacas_por_vender
            FROM "cine-dev".butaca b
            JOIN "cine-dev".sala s
            ON b.id_sala = s.id
            JOIN "cine-dev".funcion f
            ON f.id_sala = s.id
            WHERE f.fecha_hora_inicio BETWEEN :inicio AND :fin
            AND b.activo = true
    """)
    int butacasPorVenderEnDia(@Param("inicio") LocalDateTime inicio,
                              @Param("fin") LocalDateTime fin);
     */
}