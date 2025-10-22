/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.controllersVenta;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.entidades.*;
import com.losagiles.CineAgile.services.*;

import java.time.LocalDateTime;
import java.util.*;

import com.losagiles.CineAgile.services.StrategiaPrecioFuncion.FuncionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/api/venta/v1/funciones")
public class FuncionController {

    @Autowired
    private FuncionService funcionService;

    @Autowired
    private SalaButacasService salaButacasService;

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
    @GetMapping("/{idPelicula}")
    private ResponseEntity<List<FuncionesPorSedeDTO>> getFuncionesAgrupadasPelicula(
            @PathVariable Long idPelicula,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {

        return ResponseEntity.ok(funcionService.mostrarFuncionesAgrupadasPorSede(idPelicula, fecha));
    }

    @GetMapping("/{idFuncion}/butacas")
    private ResponseEntity<List<ButacaFuncionDTO>> getButacasDeFuncion(@PathVariable Long idFuncion) {
        return ResponseEntity.ok(funcionService.mostrarButacasDeUnaFuncion(idFuncion));
    }

    @GetMapping("/{idFuncion}/butacas/disponibles")
    private int consultarCantidadButacasDisponibles(@PathVariable Long idFuncion){
        return salaButacasService.consultarCantidadButacasDisponibles(idFuncion);
    }

    @GetMapping("/{idFuncion}/disponibilidad")
    public ResponseEntity<Boolean> verificarDisponibilidad(@PathVariable Long idFuncion) {
        boolean res = funcionService.estaDisponible(idFuncion);
        if (!res)
            return ResponseEntity.status(423).body(false);
        return ResponseEntity.ok(funcionService.estaDisponible(idFuncion));
    }

    @GetMapping("/precios")
    private float getPrecio(@RequestParam(required = false) Long idFuncion, @RequestParam(required = false) String persona) {
        return funcionService.precio(funcionService.getFuncionPorId(idFuncion), persona);
    }
}
