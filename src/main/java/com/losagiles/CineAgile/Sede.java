/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.List;

/**
 *
 * @author CARDENAS IGLESIAS HUGO AUGUSTO
 */


@Entity
@Table(name = "sede")
public class Sede {

    @Id
    @Column(name = "idSede")
    private int idSede;

    @Column(name = "nombre", nullable = false)
    private String nombre;


    // Relación uno a muchos con Sala
    @OneToMany(mappedBy = "sede", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sala> salas;

    // Constructor vacío requerido por JPA
    public Sede() {}

    // Constructor con parámetros
    public Sede(int idSede, String nombre) {
        this.idSede = idSede;
        this.nombre = nombre;
    }

    // Getters y Setters

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
}

