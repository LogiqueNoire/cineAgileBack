package com.losagiles.CineAgile.dto.responses;

import com.losagiles.CineAgile.entidades.Sala;
import lombok.Builder;

@Builder
public record ResCrearSala(
        ResSalaErrorCode error,
        Sala sala
) {
}
