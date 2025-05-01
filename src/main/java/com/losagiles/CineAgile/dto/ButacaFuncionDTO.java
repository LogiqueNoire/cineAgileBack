package com.losagiles.CineAgile.dto;

import com.losagiles.CineAgile.entidades.Butaca;
import com.losagiles.CineAgile.entidades.Entrada;
import lombok.Data;

@Data
public class ButacaFuncionDTO {
    private Butaca butaca;
    private boolean ocupado;

    public ButacaFuncionDTO(Butaca butaca, boolean ocupado) {
        this.butaca = butaca;
        this.ocupado = ocupado;
    }

}
