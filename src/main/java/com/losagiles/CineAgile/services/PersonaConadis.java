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
public class PersonaConadis implements Personeable{
    @Override
    public float precio(float precioAnterior) { 
        return (float) 0.7*precioAnterior;
    }
    
}
