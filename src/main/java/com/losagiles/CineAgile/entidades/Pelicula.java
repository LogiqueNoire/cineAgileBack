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

    public Pelicula() {}

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

    public long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public LocalDate getFechaInicioPreventa() {
        return fechaInicioPreventa;
    }

    public void setFechaInicioPreventa(LocalDate fechaInicioPreventa) {
        this.fechaInicioPreventa = fechaInicioPreventa;
    }

    public LocalDate getFechaInicioEstreno() {
        return fechaInicioEstreno;
    }

    public void setFechaInicioEstreno(LocalDate fechaInicioEstreno) {
        this.fechaInicioEstreno = fechaInicioEstreno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
