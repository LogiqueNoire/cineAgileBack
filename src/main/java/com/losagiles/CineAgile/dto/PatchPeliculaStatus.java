package com.losagiles.CineAgile.dto;

public enum PatchPeliculaStatus {
    NO_ERROR("La película se ha editado", 200),
    PELICULA_NO_EXISTE("La película no existe", 422);

    private final String descripcion;
    private final int httpStatus;

    PatchPeliculaStatus(String descripcion, int httpStatus) {
        this.descripcion = descripcion;
        this.httpStatus = httpStatus;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
