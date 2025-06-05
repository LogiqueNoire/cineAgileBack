/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.dto.ReqRegistrarEntrada;
import com.losagiles.CineAgile.dto.ResRegistrarEntrada;
import com.losagiles.CineAgile.entidades.Entrada;
import com.losagiles.CineAgile.services.EntradaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author JOSE
 */
@RestController
@RequestMapping("/entrada")
public class EntradaREST {

    @Autowired
    private EntradaService entradaService;

    @GetMapping
    private ResponseEntity<List<Entrada>> getAllEntradas (){
        return ResponseEntity.ok(entradaService.listarEntradas());
    }

    @PostMapping
    private ResponseEntity<ResRegistrarEntrada> postEntradas(@RequestBody ReqRegistrarEntrada infoEntrada) {
        System.out.println("Registrando");
        ResRegistrarEntrada res = entradaService.registrarEntradas(infoEntrada);
        return ResponseEntity.ok(res);
    }

}
