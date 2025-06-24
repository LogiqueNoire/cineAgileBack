package com.losagiles.CineAgile.dto;

public enum ResRegEntradaStatusCode {
    OK("Entradas compradas.", 200),
    OK_RESERVA("Entradas reservadas.", 200),
    OK_LIBERAR("Entradas liberadas.", 200),
    NRO_ENTRADAS_INVALIDAS("El nro. de entradas debe estar entre 1-5.", 422),
    FUNCION_INVALIDA("La función no existe.", 422),
    FECHA_INCORRECTA("La(s) entrada(s) deben comprarse antes del comienzo de la función", 422),
    BUTACAS_INCORRECTAS("La información de butaca(s) es incorrecta.", 422),
    BUTACAS_OCUPADAS("La(s) butaca(s) ya están ocupadas..", 422)
    ;

    private final String descripcion;
    private final int httpStatus;

    ResRegEntradaStatusCode(String descripcion, int httpStatus) {
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
