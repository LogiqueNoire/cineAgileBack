package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.dto.PeliculaDTO;
import com.losagiles.CineAgile.dto.SalaDTO;
import com.losagiles.CineAgile.dto.SedeDTO;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.services.PeliculaService;
import com.losagiles.CineAgile.services.SalaButacasService;
import com.losagiles.CineAgile.services.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/intranet")
public class IntranetController {
    @Autowired
    PeliculaService peliculaService;

    @Autowired
    SedeService sedeService;

    @Autowired
    SalaButacasService salaButacasService;

    @GetMapping("/peliculas")
    public List<Pelicula> findAll() {
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
        pelicula.setFechaInicioEstreno(fechaEstreno);

        pelicula.setImageUrl(dto.getImageUrl());
        pelicula.setSinopsis(dto.getSinopsis());

        peliculaService.agregarPelicula(pelicula);
        return ResponseEntity.ok(pelicula);
    }

    @GetMapping("/sedesysalas")
    public List<Sede> getSedes() {
        return sedeService.findAll();
    }

    @PostMapping("/sedesysalas/agregar")
    private ResponseEntity<Sede> addSede(@RequestBody SedeDTO dto) {
        Sede sede = new Sede();
        sede.setNombre(dto.getNombre());
        sedeService.agregarSede(sede);

        return ResponseEntity.ok(sede);
    }

    @PostMapping("/sedesysalas/nuevaSala")
    public ResponseEntity<Sala> agregarSala(@RequestBody SalaDTO salaDTO) {
        Optional<Sede> sedeOptional = sedeService.findById(salaDTO.getIdSede());

        Sede sede = sedeOptional.get();

        Sala sala = new Sala();
        sala.setCodigoSala(salaDTO.getCodigoSala());
        sala.setCategoria(salaDTO.getCategoria());
        sala.setSede(sede);

        Sala guardada = salaButacasService.save(sala);

        return ResponseEntity.ok(guardada);
    }


}
