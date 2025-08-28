/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services.StrategiaPrecioFuncion;

import jakarta.persistence.Embeddable;

/**
 *
 * @author USUARIO
 */
@Embeddable
public class PersonaGeneral implements Personeable{
    @Override
    public float precio(float precioAnterior) {
        return precioAnterior;
    }
    
}
