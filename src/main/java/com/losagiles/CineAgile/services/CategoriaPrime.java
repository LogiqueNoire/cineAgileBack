/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services;

import jakarta.persistence.Embeddable;

/**
 *
 * @author USUARIO
 */
@Embeddable
public class CategoriaPrime implements Categorizable {
    @Override
    public float precio(float precioBase){
        return (float) (precioBase*0.5);
    }
}
