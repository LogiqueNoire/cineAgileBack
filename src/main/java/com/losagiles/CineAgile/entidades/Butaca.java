/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author USUARIO
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Butaca {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int fila;

    @Column(nullable = false)
    private int columna;

    @Column(nullable = false)
    private boolean discapacitado;

    @Column(nullable = false)
    private boolean activo;

    @ManyToOne
    @JoinColumn (name="id_sala")
    @JsonBackReference
    private Sala sala;

}
