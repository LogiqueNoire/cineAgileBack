package com.losagiles.CineAgile.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("casa")
public class CasaREST {
    @Autowired
    CasaService casaService;

    @GetMapping ("/{id}")
    public ResponseEntity<Casa> getCasa(@PathVariable Long id) {
        Casa casa = casaService.mostrarCasa(id);
        System.out.println(casa);
        return ResponseEntity.ofNullable(casa);
    }

    @GetMapping
    public ResponseEntity<List<Casa>> getCasas() {
        return ResponseEntity.ok(casaService.listarCasas());
    }

    @PostMapping
    public String registrarCasa(@RequestBody Casa casa) {
        Casa casaCreada = casaService.registrarCasa(casa);
        if (casaCreada != null) {
            return "Casa creada!";
        } else {
            return "No se pudo crear la casa! :(";
        }
    }
}
