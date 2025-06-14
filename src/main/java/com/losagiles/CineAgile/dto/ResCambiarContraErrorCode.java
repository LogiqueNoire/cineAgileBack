package com.losagiles.CineAgile.dto;

public enum ResCambiarContraErrorCode {
    NO_ERROR(null),
    CONTRA_ACTUAL_INVALIDA("La contraseña actual ingresada no es válida."),
    LONGITUD_INVALIDA("La contraseña tiene menos de 8 caracteres."),
    FORMATO_INVALIDO("La contraseña no cumple con los requerimientos (Contiene caracteres no alfanuéricos, no contiene ningún número, o no contiene ninguna mayúscula).");

    private final String descripcion;

    ResCambiarContraErrorCode(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
