package com.losagiles.CineAgile.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NombreDTO {
    private Long id;
    private String nombre;
    private Boolean activo;

    public NombreDTO(Long id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }

    public NombreDTO(Long id, String nombre, Boolean activo){
        this.id=id;
        this.nombre=nombre;
        this.activo=activo;
    }
}
