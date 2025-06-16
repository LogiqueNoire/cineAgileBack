package com.losagiles.CineAgile.dto;


import com.losagiles.CineAgile.entidades.Sala;
import lombok.Data;

import java.util.List;

@Data
public class SedeDTO {
    private String nombre;
    private List<Sala> salas;
    private boolean activo;
}
