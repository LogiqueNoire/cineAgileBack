package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.services.PeliculaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pelicula")
public class PeliculaREST {
    @Autowired
    PeliculaService peliculaService;

    @GetMapping ("/estreno")
    private ResponseEntity <List<Pelicula>> getEstreno(){
        return ResponseEntity.ok(peliculaService.mostrarPeliculasEstreno());
    }

    @GetMapping ("/proximamente")
    private ResponseEntity <List<Pelicula>> getProximamente(){
        return ResponseEntity.ok(peliculaService.mostrarPeliculasProximamente());
    }


}