package com.losagiles.CineAgile.dto;

import com.losagiles.CineAgile.entidades.Usuario;
import lombok.Builder;

@Builder
public record ResCrearUsuario(
        ResUsuarioErrorCode error,
        Usuario usuario
) {
    public static ResCrearUsuario Error(ResUsuarioErrorCode error) {
        return ResCrearUsuario.builder().error(error).build();
    }
}
