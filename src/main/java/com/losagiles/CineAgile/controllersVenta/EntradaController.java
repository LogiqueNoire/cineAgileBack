/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.controllersVenta;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.dto.responses.ResComprarEntrada;
import com.losagiles.CineAgile.dto.responses.ResRegistrarEntrada;
import com.losagiles.CineAgile.services.EntradaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author JOSE
 */
@RestController
@RequestMapping("/api/venta/v1/entradas")
public class EntradaController {

    @Autowired
    private EntradaService entradaService;

    @GetMapping
    private ResponseEntity<?> getEntradas(@RequestParam String token){
        if(token!=null){
            EntradasCompradasDTO entradasCompradasDTO = entradaService.findEntrada(token);
            if (entradasCompradasDTO == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("No se encontró ninguna entrada con ese token.");
            }
            return ResponseEntity.ok(entradasCompradasDTO);
        }
        return ResponseEntity.ok(entradaService.listarEntradas());
    }

    @PostMapping
    private ResponseEntity<ResComprarEntrada> postEntradas(@RequestBody ReqRegistrarEntrada infoEntrada) {
        ResRegistrarEntrada res = entradaService.registrarEntradas(infoEntrada);
        return ResponseEntity
                .status(res.status().getHttpStatus())
                .body(new ResComprarEntrada(res.entradasCompradas(), res.status().getDescripcion()));
    }

    @PostMapping("/lock")
    public ResponseEntity<String> lockEntrada(@RequestBody ReqRegistrarEntrada reqRegistrarEntrada) {
        ResRegistrarEntrada res = entradaService.lockEntradas(reqRegistrarEntrada);
        return ResponseEntity.status(res.status().getHttpStatus()).body(res.status().getDescripcion());
    }

    @PostMapping("/unlock")
    public ResponseEntity<String> unlockEntrada(@RequestBody ReqRegistrarEntrada reqRegistrarEntrada) {
        ResRegistrarEntrada res = entradaService.unlock(reqRegistrarEntrada);
        return ResponseEntity.status(res.status().getHttpStatus()).body(res.status().getDescripcion());
    }
}
