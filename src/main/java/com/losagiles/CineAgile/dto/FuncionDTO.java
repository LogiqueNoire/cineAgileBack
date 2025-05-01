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
    private Long idSala;
    private String categoria;
    private String codigoSala;

    public FuncionDTO(Long idFuncion, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String dimension,
            float precioBase, int idSede, String nombreSede, Long idSala, String categoria, String codigoSala) {
        this.idFuncion = idFuncion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.dimension = dimension;
        this.precioBase = precioBase;
        this.idSede = idSede;
        this.nombreSede = nombreSede;
        this.idSala = idSala;
        this.categoria = categoria;
        this.codigoSala=codigoSala;
    }
}
