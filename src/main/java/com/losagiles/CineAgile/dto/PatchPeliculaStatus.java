package com.losagiles.CineAgile.dto;

public enum PatchPeliculaStatus {
    NO_ERROR("La película se ha editado", 200),
    GENEROS_INVALIDOS("Los géneros no existen", 422),
    NOMBRE_REPETIDO("Ya existe una película con el mismo nombre", 409),
    SUPERA_LIMITE_CARACTERES("Supera los límites de caracteres", 422),
    DURACION_FUERA_DE_RANGO("La duración está fuera del rango (1-500 min).", 422),
    ERROR_INTERNO("Ha ocurrido un error interno.", 422),
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
