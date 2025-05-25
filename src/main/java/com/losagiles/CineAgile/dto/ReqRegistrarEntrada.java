package com.losagiles.CineAgile.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ReqRegistrarEntrada(
    long id_funcion,
    List<EntradaInfo> entradas,
    LocalDateTime tiempoRegistro
) {}