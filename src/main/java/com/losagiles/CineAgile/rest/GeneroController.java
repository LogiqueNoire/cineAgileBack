package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.entidades.Genero;
import com.losagiles.CineAgile.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/generos")
public class GeneroController {
    @Autowired
    GeneroService generoService;

    @GetMapping
    public ResponseEntity<List<Genero>> getGenerosDePelicula(@RequestParam Long pelicula){
        return ResponseEntity.ok(generoService.findAllByPeliculaId(pelicula));
    }
}
