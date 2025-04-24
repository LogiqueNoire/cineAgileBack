/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author USUARIO
 */
public class Sala {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int idSala;
    private int numeroSala;
    private int capacidad;
    private String categoria;
    
    
    
    public Sala(int numeroSala, int capacidad, String categoria) {
        this.numeroSala = numeroSala;
        this.capacidad = capacidad;
        this.categoria = categoria;
    }
    

    @Override
    public String toString() {
        return "Sala: "+getIdSala()+"\tNumero: "+getNumeroSala()+"\tCapacidad: "+getCapacidad()+"\tCategoria: "+getCategoria();
    }


    public String getCategoria() {
        return categoria;
    }

    public int getNumeroSala() {
        return numeroSala;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getIdSala() {
        return idSala;
    }


}