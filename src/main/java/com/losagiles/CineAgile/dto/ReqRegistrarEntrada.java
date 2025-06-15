package com.losagiles.CineAgile.dto;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public record ReqRegistrarEntrada(
        @NonNull Long id_funcion,
        @NonNull List<EntradaInfo> entradas,
        @NonNull LocalDateTime tiempoRegistro
) {}