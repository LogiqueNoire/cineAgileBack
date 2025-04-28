/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.entidades;

import jakarta.persistence.*;

/**
 *
 * @author USUARIO
 */
@Entity
public class Butaca {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private short fila;

    @Column(nullable = false)
    private short columna;

    @Column(nullable = false)
    private boolean discapacitado;

    @Column(nullable = false)
    private boolean ocupado;
    
    @ManyToOne
    @JoinColumn (name="id_sala")
    private Sala sala;

}
