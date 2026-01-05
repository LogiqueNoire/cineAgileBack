package com.losagiles.CineAgile.dto.responses;

public record RespuestaLogin(
        String token,
        String error
) {
}
