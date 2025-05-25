package com.losagiles.CineAgile.dto;

import com.losagiles.CineAgile.entidades.Entrada;
import com.losagiles.CineAgile.entidades.Funcion;

import java.time.LocalDateTime;
import java.util.List;

public record ResRegistrarEntrada(
        List<Entrada> entradas,
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin,
        String nombreSede,
        String sala,
        String tituloPelicula
) { }
