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
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author aryel:v
 */
@Entity
@Table (name = "Pelicula")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long idPelicula;
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
    LocalDate   fechaInicioPreventa;
    @Column
    LocalDate   fechaInicioEstreno;
    @Column
    String estado;

    public Pelicula(String nombre, int duracion, String sinopsis, 
                    String genero, String director, String clasificacion, String actores, 
                    LocalDate fechaInicioPreventa, LocalDate fechaInicioEstreno, String estado) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titulo: ").append(nombre).append("\n");
        sb.append("Duracion: ").append(duracion).append("\n");
        sb.append("Sinopsis: ").append(sinopsis).append("\n");
        sb.append("Genero: ").append(genero).append("\n");
        sb.append("Director: ").append(director).append("\n");
        sb.append("Clasificacion: ").append(clasificacion).append("\n");
        sb.append("Actores: ").append(actores).append("\n");
        sb.append("Inicio de Preventa: ").append(fechaInicioPreventa).append("\n");
        sb.append("Estreno: ").append(fechaInicioEstreno).append("\n");
        sb.append("Estado: ").append(estado).append("\n");
        return sb.toString();
    }
    
    
}
