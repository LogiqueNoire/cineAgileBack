package com.losagiles.CineAgile.dto;

import lombok.Data;

@Data
public class NombreDTO {
    private Long id;
    private String nombre;

    public NombreDTO(Long id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }
}
