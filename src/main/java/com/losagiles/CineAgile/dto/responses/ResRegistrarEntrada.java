package com.losagiles.CineAgile.dto.responses;

import com.losagiles.CineAgile.dto.EntradasCompradasDTO;

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
