package com.losagiles.CineAgile.services;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author USUARIO
 */
public class CategoriaRegular implements Categorizable{
    @Override
    public float precio(float precioBase) {
        return precioBase;
    }
}
