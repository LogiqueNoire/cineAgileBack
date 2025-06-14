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
            where b.sala.id = (
            	select sala.id from Funcion f where f.id = :funcionid
            )
            """)
    public int consultarCantidadButacasDisponibles(@Param("funcionid") Long idFuncion);
}