/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.repository;
import com.losagiles.CineAgile.entidades.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author JOSE
 */

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    List<Entrada> findAllByButacaIdIn(Iterable<Long> ids);

    @Query("""
              select count(e.id.idFuncion) != 0
              from Entrada e
              where e.id.idFuncion = :id_funcion
          """
    )
    public boolean tieneEntradas(@Param("id_funcion") Long idFuncion);

}
