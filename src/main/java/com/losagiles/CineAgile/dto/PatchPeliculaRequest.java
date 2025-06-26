package com.losagiles.CineAgile.dto;

import com.losagiles.CineAgile.entidades.Genero;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

public record PatchPeliculaRequest(
        @NonNull Long idPelicula,
        String nombre,
        Integer duracion,
        String director,
        String clasificacion,
        String actores,
        String urlImagen,
        LocalDate fechaEstreno,
        String sinopsis,
        List<Genero> generos
) {
}