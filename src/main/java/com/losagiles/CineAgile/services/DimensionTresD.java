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
public class DimensionTresD implements Dimensionable{
    @Override
    public float precio() {
        return 5;
    }
    
}
