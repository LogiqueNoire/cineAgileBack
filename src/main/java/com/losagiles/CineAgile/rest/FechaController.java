package com.losagiles.CineAgile.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class FechaController {

    @GetMapping("/fecha-actual")
    public String obtenerFechaActual() {
        LocalDate fechaActual = LocalDate.now(); // obtiene la fecha actual sin tiempo
        return fechaActual.toString(); // devuelve en formato ISO-8601 (yyyy-MM-dd)
    }
}
