package com.losagiles.CineAgile.dto;

import lombok.NonNull;

public record SolicitudButacaAccion(
        @NonNull Long idButaca,
        @NonNull String accion
) {
}
