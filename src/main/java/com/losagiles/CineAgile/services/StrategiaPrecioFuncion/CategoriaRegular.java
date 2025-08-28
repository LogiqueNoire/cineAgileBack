package com.losagiles.CineAgile.services.StrategiaPrecioFuncion;

import jakarta.persistence.Embeddable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author USUARIO
 */
@Embeddable
public class CategoriaRegular implements Categorizable {
    @Override
    public float precio(float precioBase) {
        return 0;
    }
}
