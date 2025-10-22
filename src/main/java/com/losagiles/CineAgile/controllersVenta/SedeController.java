/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.controllersVenta;

import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.services.SedeService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author CARDENAS IGLESIAS HUGO AUGUSTO
 */
@RestController
@RequestMapping("/api/v1/sedes")
public class SedeController {
    @Autowired
    private SedeService sedeService;

    @GetMapping ("/{idSede}/salas")
    private ResponseEntity<List<Sala>> getSedes(@PathVariable long idSede){
        return ResponseEntity.ok(sedeService.mostrarSalasDeSede(idSede));
    }
}
