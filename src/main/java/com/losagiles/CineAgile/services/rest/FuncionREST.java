/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services.rest;

import com.losagiles.CineAgile.Funcion;
import com.losagiles.CineAgile.services.FuncionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USUARIO
 */

@RestController
@RequestMapping ("/pais")
public class FuncionREST {
    @Autowired
    private FuncionService funcionService;
    
    @GetMapping
    private ResponseEntity<List<Funcion>> getAllFunciones(){
        return ResponseEntity.ok(funcionService.findAll());
        
    }
}
