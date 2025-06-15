package com.losagiles.CineAgile.dto;

import com.losagiles.CineAgile.entidades.Entrada;
import com.losagiles.CineAgile.entidades.Funcion;

import java.time.LocalDateTime;
import java.util.List;

public record ResRegistrarEntrada(
        EntradasCompradasDTO entradasCompradas,
        ResRegEntradaStatusCode status
) {
    public static ResRegistrarEntrada error(ResRegEntradaStatusCode status) {
        return new ResRegistrarEntrada(null, status);
    }

    public static ResRegistrarEntrada ok(EntradasCompradasDTO body) {
        return new ResRegistrarEntrada(body, ResRegEntradaStatusCode.OK);
    }
}
