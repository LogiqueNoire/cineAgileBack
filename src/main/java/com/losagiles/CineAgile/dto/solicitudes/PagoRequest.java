package com.losagiles.CineAgile.dto.solicitudes;

import com.losagiles.CineAgile.dto.ReqRegistrarEntrada;
import lombok.Data;

@Data
public class PagoRequest {
    private String token;
    private String paymentMethodId;
    private String issuerId;
    private String email;
    private float monto;
    private String idOperacion;
    private ReqRegistrarEntrada entradas;
}

