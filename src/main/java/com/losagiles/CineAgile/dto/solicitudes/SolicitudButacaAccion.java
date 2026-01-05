package com.losagiles.CineAgile.dto.solicitudes;

import lombok.NonNull;

public record SolicitudButacaAccion(
        @NonNull Long idButaca,
        @NonNull String accion
) {
}
