/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author mvela
 */

package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.dto.SalaDTO;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    //findAllBy + Sede (la propiedad) + _Id (el campo id de la entidad Sede).
    public List<Sala> findAllBySede_IdAndActivoTrue(@Param("idSede") Long idSede);

    public boolean existsBySedeAndCodigoSala(Sede sede, String codigoSala);

    public Optional<Sala> findById(Long id);
}
