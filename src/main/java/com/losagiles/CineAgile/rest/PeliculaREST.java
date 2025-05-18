package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.dto.PeliculaCarteleraDTO;
import com.losagiles.CineAgile.dto.PeliculaDTO;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.services.PeliculaService;

import java.time.LocalDate;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/pelicula")
public class PeliculaREST {
    @Autowired
    PeliculaService peliculaService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping ("/{idPelicula}")
    private ResponseEntity<Pelicula> getPelicula(@PathVariable Long idPelicula) {
        return ResponseEntity.ofNullable(peliculaService.mostrarPelicula(idPelicula));
    }

    @GetMapping ("/encartelera")
    private ResponseEntity <List<PeliculaCarteleraDTO>> getEstreno(){
        List<PeliculaCarteleraDTO> peliculas = peliculaService.mostrarPeliculasEnCartelera();

        // Mapear a PeliculaCarteleraDTO, el cual solo muestra información muy básica
        List<PeliculaCarteleraDTO> peliDTO = modelMapper.map(peliculas, new TypeToken<List<PeliculaCarteleraDTO>>() {}.getType());

        return ResponseEntity.ok(peliDTO);
    }

    @GetMapping ("/proximamente")
    private ResponseEntity <List<PeliculaCarteleraDTO>> getProximamente(){
        List<PeliculaCarteleraDTO> peliculas = peliculaService.mostrarPeliculasProximamente();
        return ResponseEntity.ok(peliculas);
    }

    @PostMapping ("/agregar")
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