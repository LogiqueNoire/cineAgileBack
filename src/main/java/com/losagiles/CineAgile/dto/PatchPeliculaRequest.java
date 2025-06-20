package com.losagiles.CineAgile.dto;

import lombok.NonNull;

import java.time.LocalDate;

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
        String genero
) {
}