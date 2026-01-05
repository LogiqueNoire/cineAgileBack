package com.losagiles.CineAgile.dto.responses;

import java.math.BigDecimal;

public record RespuestaPago(
        String idOperacion,
        String method,
        String status,
        String statusDetail,
        BigDecimal monto,
        ResComprarEntrada resultadoRegistroEntradas
) {}
