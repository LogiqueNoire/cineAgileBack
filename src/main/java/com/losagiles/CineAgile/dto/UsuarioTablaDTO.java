package com.losagiles.CineAgile.dto;

import lombok.Builder;

@Builder
public record UsuarioTablaDTO(
        String username,
        String nombreSede
) {
}
