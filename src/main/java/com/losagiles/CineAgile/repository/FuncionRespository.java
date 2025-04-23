/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USUARIO
 */

package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionRespository extends JpaRepository<Funcion, Long>{
    
}
