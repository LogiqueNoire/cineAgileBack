package com.losagiles.CineAgile.dto;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record SolicitudCrearUsuario(
        @NonNull String username,
        @NonNull String password,
        @NonNull Long sedeId
) {
}
