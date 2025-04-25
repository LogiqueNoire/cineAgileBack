/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author USUARIO
 */
@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idSala;
    private int numeroSala;
    private int capacidad;
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "sedeId") // este nombre puede variar según tu tabla
    private Sede sede;

    public Sala(int numeroSala, int capacidad, String categoria) {
        this.numeroSala = numeroSala;
        this.capacidad = capacidad;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Sala: " + getIdSala() + "\tNumero: " + getNumeroSala() + "\tCapacidad: " + getCapacidad() + "\tCategoria: " + getCategoria();
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

    public Long getIdSala() {
        return idSala;
    }

}
