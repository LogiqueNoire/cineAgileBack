/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services.rest;

import com.losagiles.CineAgile.Entrada;
import com.losagiles.CineAgile.services.EntradaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JOSE
 */
@RestController
@RequestMapping("/entrada/")
public class EntradaREST {
    
        @Autowired
        private EntradaService entradaService;
        
        @GetMapping
        private ResponseEntity<List<Entrada>> getAllEntradas (){
            return ResponseEntity.ok(entradaService.findAll());
        }
        
}
