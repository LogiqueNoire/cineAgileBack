package com.losagiles.CineAgile.dto;

import lombok.Data;

@Data
public class PeliculaCarteleraDTO {
    private Long idPelicula;
    private String nombre;
    private String estado;
    private String imageUrl;
    private String sinopsis;
}