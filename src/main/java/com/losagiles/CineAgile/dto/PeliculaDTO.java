package com.losagiles.CineAgile.dto;

import com.losagiles.CineAgile.entidades.Genero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PeliculaDTO {
    private Long idPelicula;
    private String nombre;
    private String director;
    private String actores;
    private List<Genero> genero;
    private String clasificacion;
    private Integer duracion;
    private String estado;
    private String fechaInicioEstreno;
    private String imageUrl;
    private String sinopsis;

    public PeliculaDTO(Long idPelicula, String nombre, String director, String actores, List<Genero> genero, String clasificacion, Integer duracion, String estado, String fechaInicioEstreno, String imageUrl, String sinopsis) {
        this.idPelicula = idPelicula;
        this.nombre = nombre;
        this.director = director;
        this.actores = actores;
        this.genero = genero;
        this.clasificacion = clasificacion;
        this.duracion = duracion;
        this.estado = estado;
        this.fechaInicioEstreno = fechaInicioEstreno;
        this.imageUrl = imageUrl;
        this.sinopsis = sinopsis;
    }

    public PeliculaDTO(String nombre, String director, String actores, List<Genero> genero, String clasificacion, Integer duracion, String estado, String fechaInicioEstreno, String imageUrl, String sinopsis) {

        this.nombre = nombre;
        this.director = director;
        this.actores = actores;
        this.genero = genero;
        this.clasificacion = clasificacion;
        this.duracion = duracion;
        this.estado = estado;
        this.fechaInicioEstreno = fechaInicioEstreno;
        this.imageUrl = imageUrl;
        this.sinopsis = sinopsis;
    }
}
