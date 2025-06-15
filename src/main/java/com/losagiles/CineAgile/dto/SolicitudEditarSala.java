package com.losagiles.CineAgile.dto;

import lombok.NonNull;

public record SolicitudEditarSala(
        @NonNull Long idSala,
        @NonNull String codigoSala,
        @NonNull String categoria
) {
}