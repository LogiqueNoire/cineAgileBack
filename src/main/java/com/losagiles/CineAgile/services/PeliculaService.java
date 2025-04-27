
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService {
    @Autowired
    PeliculaRepository peliculaRepository;

    public List<Pelicula> mostrarPeliculasEstreno() {
        return peliculaRepository.getPeliculasByEstado("estreno");
    }

    public List<Pelicula> mostrarPeliculasProximamente() {
        return peliculaRepository.getPeliculasByEstado("proximamente");
    }
}
