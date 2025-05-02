package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.dto.PeliculaCarteleraDTO;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.services.PeliculaService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping ("/estreno")
    private ResponseEntity <List<PeliculaCarteleraDTO>> getEstreno(){
        List<PeliculaCarteleraDTO> peliculas = peliculaService.mostrarPeliculasEstreno();

        // Mapear a PeliculaCarteleraDTO, el cual solo muestra información muy básica
        List<PeliculaCarteleraDTO> peliDTO = modelMapper.map(peliculas, new TypeToken<List<PeliculaCarteleraDTO>>() {}.getType());

        return ResponseEntity.ok(peliDTO);
    }

    @GetMapping ("/proximamente")
    private ResponseEntity <List<PeliculaCarteleraDTO>> getProximamente(){
        List<PeliculaCarteleraDTO> peliculas = peliculaService.mostrarPeliculasProximamente();
        return ResponseEntity.ok(peliculas);
    }

}