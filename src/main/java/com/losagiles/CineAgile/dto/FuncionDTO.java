package com.losagiles.CineAgile.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long idSede;
    private String nombreSede;

    // Sala
    private Long idSala;
    private String categoria;
    private String codigoSala;

    // Pelicula
    private Long idPelicula;
    private String nombrePelicula;

    public FuncionDTO(Long idFuncion, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String dimension,
            float precioBase, Long idSede, String nombreSede, Long idSala, String categoria, String codigoSala,
            Long idPelicula, String nombrePelicula) {
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
        this.idPelicula=idPelicula;
        this.nombrePelicula=nombrePelicula;
    }
}
