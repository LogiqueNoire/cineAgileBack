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
import jakarta.persistence.ManyToOne;

/**
 *
 * @author JOSE
 */

@Entity
public class Entrada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEntrada;
    @Column
    private float costoFinal;
    @ManyToOne
    private Funcion funcion;
    /*
    ¿No deberia ser asi el ManyToOne para funcion?(Es una sugerencia y duda a la vez)xd
    @ManyToOne
    @JoinColumn(name = "id_Funcion")
    private Funcion funcion;
    */

    /*
    Ah y tambien ponerle el de butaca obvio aun no lo colocamos pq todavia no esta todo al 100% ps xd
    @ManyToOne
    @JoinColumn(name = "id_Butaca")
    private Butaca Butaca;
    */

    public Entrada() {}

    public Entrada(Long idEntrada, float costoFinal, Funcion funcion) {
        this.idEntrada = idEntrada;
        this.costoFinal = costoFinal;
        this.funcion = funcion;
    }

    public Long getIdEntrada() {
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
