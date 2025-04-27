/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.entidades;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

/**
 *
 * @author aryel:v
 */
@Data
@Entity
@Table (name = "Pelicula")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long idPelicula;

    @Column (nullable = false)
    String nombre;

    @Column (nullable = false)
    private int duracion; // En minutos

    @Column (nullable = false)
    String sinopsis;

    @Column (nullable = false)
    String genero;

    @Column (nullable = false)
    String director;

    @Column (nullable = false)
    String clasificacion;

    @Column (nullable = false)
    String actores;

    @Column (nullable = false)
    @Temporal(TemporalType.DATE)
    LocalDate fechaInicioPreventa;

    @Column (nullable = false)
    @Temporal(TemporalType.DATE)
    LocalDate fechaInicioEstreno;

    @Column (nullable = false)
    String estado;

    @Column (nullable = false)
    String imageUrl;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idPelicula:").append(idPelicula).append("\n");
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
