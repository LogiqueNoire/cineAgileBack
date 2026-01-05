package com.losagiles.CineAgile.dto.solicitudes;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record SolicitudCambiarContra(
        @NonNull String contraActual,
        @NonNull String nuevaContra
) {
}
