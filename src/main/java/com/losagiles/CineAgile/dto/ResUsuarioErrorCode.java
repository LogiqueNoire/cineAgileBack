package com.losagiles.CineAgile.dto;

import lombok.Getter;

@Getter
public enum ResUsuarioErrorCode {
    NOMBRE_USUARIO_INVALIDO("El nombre de usuario no cumple con los requisitos. (Min. 3 caracteres alfanúmeros y _)"),
    CONTRASENA_INVALIDA("La contraseña no cumple con las políticas de seguridad. (Mín. 8 caracteres alfanuméricos, un número, una mayúscula y una minúscula)"),
    SEDE_NO_EXISTE("La sede no existe."),
    USUARIO_YA_EXISTE("El usuario ya existe.");

    private final String descripcion;

    ResUsuarioErrorCode(String descripcion) {
        this.descripcion = descripcion;
    }
}
