
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.PeliculaCarteleraDTO;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {
    @Autowired
    PeliculaRepository peliculaRepository;

    public Pelicula mostrarPelicula(Long idPelicula) {
        Optional<Pelicula> pelicula = peliculaRepository.findById(idPelicula);
        
        return pelicula.orElse(null);
    }

    public List<PeliculaCarteleraDTO> mostrarPeliculasEnCartelera() {
        return peliculaRepository.getPeliculasByEstado("en cartelera");
    }

    public List<PeliculaCarteleraDTO> mostrarPeliculasProximamente() {
        return peliculaRepository.getPeliculasByEstado("proximamente");
    }

    public Pelicula agregarPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

}
