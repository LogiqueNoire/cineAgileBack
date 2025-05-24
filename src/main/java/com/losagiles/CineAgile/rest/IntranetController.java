package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.dto.PeliculaDTO;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.services.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/intranet")
public class IntranetController {
    @Autowired
    PeliculaService peliculaService;

    @GetMapping("/peliculas")
    public List<Pelicula> mostrarPeliculas() {
        return peliculaService.findAll();
    }

    @PostMapping("/peliculas/agregar")
    private ResponseEntity<Pelicula> addPelicula(@RequestBody PeliculaDTO dto) {
        Pelicula pelicula = new Pelicula();

        pelicula.setNombre(dto.getNombre());
        pelicula.setDirector(dto.getDirector());
        pelicula.setActores(dto.getActores());
        pelicula.setGenero(dto.getGenero());
        pelicula.setClasificacion(dto.getClasificacion());
        pelicula.setDuracion(dto.getDuracion());
        pelicula.setEstado(dto.getEstado());

        LocalDate fechaEstreno = LocalDate.parse(dto.getFechaInicioEstreno());
        LocalDate fechaPreventa = LocalDate.parse(dto.getFechaInicioPreventa());
        pelicula.setFechaInicioEstreno(fechaEstreno);
        pelicula.setFechaInicioPreventa(fechaPreventa);

        pelicula.setImageUrl(dto.getImageUrl());
        pelicula.setSinopsis(dto.getSinopsis());

        peliculaService.agregarPelicula(pelicula);
        return ResponseEntity.ok(pelicula);
    }
}
