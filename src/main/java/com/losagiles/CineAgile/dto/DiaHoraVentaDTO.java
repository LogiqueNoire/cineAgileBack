package com.losagiles.CineAgile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class DiaHoraVentaDTO {
    private int dia;
    private int hora;
    private BigDecimal ventas;

    public DiaHoraVentaDTO(int dia, int hora, BigDecimal ventas) {
        this.dia = dia;
        this.hora = hora;
        this.ventas = ventas;
    }
}
