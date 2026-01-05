package com.losagiles.CineAgile.dto.entidadesParciales;

import lombok.Builder;

@Builder
public record UsuarioInternoDTO(
        String username,
        String nombreSede
) {
}
