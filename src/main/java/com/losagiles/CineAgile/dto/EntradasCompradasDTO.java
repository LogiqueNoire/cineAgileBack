package com.losagiles.CineAgile.dto;

import com.losagiles.CineAgile.entidades.Entrada;

import java.time.LocalDateTime;
import java.util.List;

public record EntradasCompradasDTO(
        List<Entrada> entradas,
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin,
        String sala,
        String nombreSede,
        String tituloPelicula
) {
}
