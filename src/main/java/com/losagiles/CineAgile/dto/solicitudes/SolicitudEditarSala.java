package com.losagiles.CineAgile.dto.solicitudes;

import lombok.NonNull;

import java.util.List;

public record SolicitudEditarSala(
        @NonNull Long idSala,
        @NonNull String codigoSala,
        @NonNull String categoria,
        List<SolicitudButacaAccion> butacas
) {
}