package com.losagiles.CineAgile.dto;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record SolicitudCambiarContra(
        @NonNull String contraActual,
        @NonNull String nuevaContra
) {
}
