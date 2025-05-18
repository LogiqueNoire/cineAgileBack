/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.dto.SedeDTO;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.services.SedeService;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author CARDENAS IGLESIAS HUGO AUGUSTO
 */
@RestController
@RequestMapping("/sede")
public class SedeREST {
    @Autowired
    private SedeService sedeService;

    @GetMapping ("/{idSede}/salas")
    private ResponseEntity<List<Sala>> getSedes(@PathVariable long idSede){
        return ResponseEntity.ok(sedeService.mostrarSalasDeSede(idSede));
    }

    @PostMapping("/agregar")
    private ResponseEntity<Sede> addSede(@RequestBody SedeDTO dto) {
        Sede sede = new Sede();
        sede.setNombre(dto.getNombre());
        sedeService.agregarSede(sede);

        return ResponseEntity.ok(sede);
    }
}
