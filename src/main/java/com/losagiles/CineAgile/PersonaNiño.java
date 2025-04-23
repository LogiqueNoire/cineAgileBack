/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile;

/**
 *
 * @author USUARIO
 */
public class PersonaNiño implements Personeable{
    @Override
    public float precio(float precioBase) { 
        return (float) -0.5*precioBase;
    }
    
}
