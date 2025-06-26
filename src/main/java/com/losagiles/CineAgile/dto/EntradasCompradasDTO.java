package com.losagiles.CineAgile.dto;

import com.losagiles.CineAgile.entidades.Entrada;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record EntradasCompradasDTO(
        List<Entrada> entradas,
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin,
        String sala,
        String nombreSede,
        String tituloPelicula,
        String clasificacion,
        List<String> tokens
) {
}
