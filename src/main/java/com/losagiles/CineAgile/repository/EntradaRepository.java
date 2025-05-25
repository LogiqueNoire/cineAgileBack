/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.repository;
import com.losagiles.CineAgile.entidades.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author JOSE
 */

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    List<Entrada> findAllByButacaIdIn(Iterable<Long> ids);
}
