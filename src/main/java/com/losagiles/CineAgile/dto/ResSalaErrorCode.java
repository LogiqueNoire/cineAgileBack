package com.losagiles.CineAgile.dto;

public enum ResSalaErrorCode {
    MAX_FILA_SOBREPASADA("La fila máxima debe ser Z."),
    BUTACAS_NO_ENCONTRADAS("No hay butacas registradas."),
    EXCEPCION_INTEGRACION_DATOS("Violación de la integridad de los datos.");

    private final String descripcion;

    ResSalaErrorCode(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
