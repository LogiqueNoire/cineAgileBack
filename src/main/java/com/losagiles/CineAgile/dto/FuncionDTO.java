package com.losagiles.CineAgile.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FuncionDTO {
    // Funcion
    private Long idFuncion;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String dimension;
    private float precioBase;

    // Sede
    private int idSede;
    private String nombreSede;

    // Sala
    private String categoria;

    public FuncionDTO(Long idFuncion, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String dimension, float precioBase, int idSede, String nombreSede, String categoria) {
        this.idFuncion = idFuncion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.dimension = dimension;
        this.precioBase = precioBase;
        this.idSede = idSede;
        this.nombreSede = nombreSede;
        this.categoria = categoria;
    }
}
