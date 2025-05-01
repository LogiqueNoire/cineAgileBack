/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

/**
 *
 * @author JOSE
 */

@Data
@Entity
public class Entrada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEntrada;

    @Column
    private float costoFinal;

    @ManyToOne
    @JoinColumn (name = "id_funcion")
    @JsonBackReference
    private Funcion funcion;

    @OneToOne
    private Butaca butaca;
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

}
