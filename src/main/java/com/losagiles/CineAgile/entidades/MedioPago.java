/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MedioPago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMedioPago;
    private String nombre;
    private String logoURL;

    public MedioPago() {}

    public MedioPago(String nombre, String logoURL) {
        this.nombre = nombre;
        this.logoURL = logoURL;
    }

    @Override
    public String toString() {
        return "Medio de Pago: " + nombre + " | Logo: " + logoURL;
    }

    public Long getIdMedioPago() {
        return idMedioPago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }
}