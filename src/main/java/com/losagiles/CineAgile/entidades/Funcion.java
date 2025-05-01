/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.losagiles.CineAgile.services.Categorizable;
import com.losagiles.CineAgile.services.Dimensionable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Funcion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (nullable = false)
    private LocalDateTime fechaHoraInicio;

    @Column (nullable = false)
    private LocalDateTime fechaHoraFin;

    @Column (nullable = false)
    private String dimension;

    @Column (nullable = false)
    private float precioBase;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_sala")
    private Sala sala;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_pelicula")  // El nombre de la columna en la base de datos
    private Pelicula pelicula;
    /*Puse atributo pelicula para lo del autocalculado de la fechaHoraFin*/

    @OneToMany (mappedBy = "funcion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Entrada> entradas;

    private Categorizable categorizable;
    private Dimensionable dimensionable;

}
