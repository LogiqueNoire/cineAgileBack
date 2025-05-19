/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.entidades.*;
import com.losagiles.CineAgile.services.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/funcion")
public class FuncionREST {

    @Autowired
    private FuncionService funcionService;

    @GetMapping("/sedes/{idPelicula}")
    private ResponseEntity<List<Sede>> getAllFunciones(@PathVariable Long idPelicula) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    /*
    @GetMapping ("/pelicula/{idPelicula}")
    private ResponseEntity<List<FuncionDTO>> getFuncionesPelicula(@PathVariable Long idPelicula, @RequestParam (required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        if (fecha != null)
            return ResponseEntity.ok(funcionService.mostrarFuncionesDePeliculaDeFecha(idPelicula, fecha));

        return ResponseEntity.ok(funcionService.mostrarFuncionesDePelicula(idPelicula));
    }
     */
    @GetMapping("/pelicula/{idPelicula}")
    private ResponseEntity<List<FuncionesPorSedeDTO>> getFuncionesAgrupadasPelicula(
            @PathVariable Long idPelicula,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {

        return ResponseEntity.ok(funcionService.mostrarFuncionesAgrupadasPorSede(idPelicula, fecha));
    }

    @GetMapping("/butacas/{idFuncion}")
    private ResponseEntity<List<ButacaFuncionDTO>> getButacasDeFuncion(@PathVariable Long idFuncion) {
        return ResponseEntity.ok(funcionService.mostrarButacasDeUnaFuncion(idFuncion));
    }
    
    @GetMapping("/precios")
    private float getPrecio(@RequestParam(required = false) Long idFuncion, @RequestParam(required = false) String persona) {
        Funcion f = funcionService.getFuncionPorId(idFuncion);
        Sala s = f.getSala();
        Personeable p;
        p = switch (persona.toLowerCase()) {
            case "general" -> new PersonaGeneral();
            case "mayores" -> new PersonaMayor();
            case "conadis" -> new PersonaConadis();
            case "niños" -> new PersonaNiño();
            default -> null;
        };
        switch(f.getDimension().toUpperCase()){
            case "2D" -> f.setDimensionable(new DimensionDosD());
            case "3D" -> f.setDimensionable(new DimensionTresD());
            default -> {}
        }
        switch(s.getCategoria().toUpperCase()){
            case "REGULAR" -> f.setCategorizable(new CategoriaRegular());
            case "PRIME" -> f.setCategorizable(new CategoriaPrime());
            default -> {}
        }
        
        
        return (float) (Math.round(funcionService.precio(f, p) * 100.0) / 100.0);
    }
    
    
}
