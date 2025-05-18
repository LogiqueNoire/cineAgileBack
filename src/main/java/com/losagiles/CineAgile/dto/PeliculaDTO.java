package com.losagiles.CineAgile.dto;

import lombok.Data;

@Data
public class PeliculaDTO {
    private Long idPelicula;
    private String nombre;
    private String director;
    private String actores;
    private String genero;
    private String clasificacion;
    private Integer duracion;
    private String estado;
    private String fechaInicioEstreno;
    private String fechaInicioPreventa;
    private String imageUrl;
    private String sinopsis;

    public PeliculaDTO(String nombre, String director, String actores, String genero, String clasificacion, Integer duracion, String estado, String fechaInicioEstreno, String fechaInicioPreventa, String imageUrl, String sinopsis) {

        this.nombre = nombre;
        this.director = director;
        this.actores = actores;
        this.genero = genero;
        this.clasificacion = clasificacion;
        this.duracion = duracion;
        this.estado = estado;
        this.fechaInicioEstreno = fechaInicioEstreno;
        this.fechaInicioPreventa = fechaInicioPreventa;
        this.imageUrl = imageUrl;
        this.sinopsis = sinopsis;
    }
}
