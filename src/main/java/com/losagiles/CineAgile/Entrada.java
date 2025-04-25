/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author JOSE
 */

@Entity
public class Entrada {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int idEntrada;
    @Column
    private float costoFinal;
    Funcion funcion;

    public Entrada(int idEntrada, float costoFinal, Funcion funcion) {
        this.idEntrada = idEntrada;
        this.costoFinal = costoFinal;
        this.funcion = funcion;
    }

    public int getIdEntrada() {
        return idEntrada;
    }

    public float getCostoFinal() {
        return costoFinal;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    @Override
    public String toString() {
        return "Entrada: " +getIdEntrada()  +"\tCosto Final: "+getCostoFinal() + "\tFuncion: "+getFuncion();
    }
    
}
