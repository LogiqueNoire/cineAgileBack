package com.losagiles.CineAgile.dto.responses;

import com.losagiles.CineAgile.dto.EntradasCompradasDTO;

public record ResComprarEntrada(
        EntradasCompradasDTO entradasCompradasDTO,
        String estado
) {
}
