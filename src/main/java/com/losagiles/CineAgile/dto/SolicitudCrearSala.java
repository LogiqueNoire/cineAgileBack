package com.losagiles.CineAgile.dto;

import com.losagiles.CineAgile.entidades.Butaca;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public record SolicitudCrearSala(
        @NonNull Long idSede,
        @NonNull String codigoSala,
        @NonNull String categoria,
        @NonNull List<Butaca> butacas
        )
{}
