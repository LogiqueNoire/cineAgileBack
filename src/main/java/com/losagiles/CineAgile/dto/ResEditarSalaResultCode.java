package com.losagiles.CineAgile.dto;

public enum ResEditarSalaResultCode {
    NO_ERROR("Sala editada."),
    BUTACAS_INVALIDAS("Las butacas especificadas son inválidas."),
    ALL_BUTACAS_DESACTIVADAS("Debe haber por lo menos 1 butaca activada."),
    CODIGO_VACIO("El nuevo código está en blanco."),
    CATEGORIA_INVALIDA("La nueva categoría es inválida."),
    SALA_INVALIDA("La sala no existe"),
    CODIGO_INVALIDO("El código de la sala ya está en uso.");

    private String descripcion;

    ResEditarSalaResultCode(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
