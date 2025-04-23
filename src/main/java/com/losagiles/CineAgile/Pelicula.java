/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

/**
 *
 * @author aryel:v
 */
@Entity
@Table (name = "Pelicula")
public class Pelicula {
    @Id
    int idPelicula;
    @Column
    String nombre;
    @Column
    private int duracion;
    @Column
    String sinopsis;
    @Column
    String genero;
    @Column
    String director;
    @Column
    String clasificacion;
    @Column
    String actores;
    @Column
    Date   fechaInicioPreventa;
    @Column
    Date   fechaInicioEstreno;
    @Column
    String estado;

    public Pelicula(int idPelicula, String nombre, int duracion, String sinopsis, String genero, String director, String clasificacion, String actores, Date fechaInicioPreventa, Date fechaInicioEstreno, String estado) {
        this.idPelicula = idPelicula;
        this.nombre = nombre;
        this.duracion = duracion;
        this.sinopsis = sinopsis;
        this.genero = genero;
        this.director = director;
        this.clasificacion = clasificacion;
        this.actores = actores;
        this.fechaInicioPreventa = fechaInicioPreventa;
        this.fechaInicioEstreno = fechaInicioEstreno;
        this.estado = estado;
    }

    public int getDuracion() {
        return duracion;
    }
    
    
}
