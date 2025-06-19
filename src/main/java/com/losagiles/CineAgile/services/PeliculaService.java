
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.NombreDTO;
import com.losagiles.CineAgile.dto.PeliculaCarteleraDTO;
import com.losagiles.CineAgile.dto.PeliculaDTO;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<PeliculaCarteleraDTO> mostrarPeliculasEnCartelera(LocalDateTime fechaDesdeFront) {
        LocalDate fecha = fechaDesdeFront.toLocalDate();
        return peliculaRepository.getPeliculasEnCartelera(fecha, fechaDesdeFront);
    }

    public List<PeliculaCarteleraDTO> mostrarPeliculasProximamente(LocalDateTime fechaDesdeFront) {
        LocalDate fecha = fechaDesdeFront.toLocalDate();
        return peliculaRepository.getPeliculasProximamente(fecha);
    }

    public Pelicula agregarPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    public List<PeliculaDTO> obtenerPeliculasConEstado(LocalDateTime fechaDesdeFront) {
        LocalDate fecha = fechaDesdeFront.toLocalDate();
        return peliculaRepository.obtenerPeliculasConEstado(fecha, fechaDesdeFront);
    }

    public List<Pelicula> findAll() {
        return peliculaRepository.findAll();
    }

    public List<NombreDTO> getNombresPeliculas(Long idSede) { return peliculaRepository.getNombresPeliculas(idSede); }

}
