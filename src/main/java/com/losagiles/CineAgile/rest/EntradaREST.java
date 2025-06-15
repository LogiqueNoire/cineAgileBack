/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.dto.*;
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
    private ResponseEntity<ResComprarEntrada> postEntradas(@RequestBody ReqRegistrarEntrada infoEntrada) {
        ResRegistrarEntrada res = entradaService.registrarEntradas(infoEntrada);
        return ResponseEntity
                .status(res.status().getHttpStatus())
                .body(new ResComprarEntrada(res.entradasCompradas(), res.status().getDescripcion()));
    }

}
