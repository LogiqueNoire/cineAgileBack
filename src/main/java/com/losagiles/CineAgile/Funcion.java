/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile;

import com.losagiles.CineAgile.services.Categorizable;
import com.losagiles.CineAgile.services.Dimensionable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 *
 * @author USUARIO
 */
@Entity
public class Funcion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long idFuncion;
    @Column
    LocalDateTime fechaHoraInicio;
    @Column
    LocalDateTime fechaHoraFin;

    @ManyToOne
    @JoinColumn(name = "id_pelicula")  // El nombre de la columna en la base de datos
    private Pelicula pelicula;

    /*Puse atributo pelicula para lo del autocalculado de la fechaHoraFin*/
    @Column
    String dimension;
    @Column
    float precioBase;

    @ManyToOne
    @JoinColumn(name = "id_Sala")
    Sala sala;

    Categorizable categorizable;
    Dimensionable dimensionable;


   
    /*Borré el id del constructor pq ya tiene el generatedValue*/
    public Funcion(LocalDateTime fechaHoraInicio, Pelicula pelicula, String dimension, float precioBase,
            Sala sala, Categorizable categorizable, Dimensionable dimensionable) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.pelicula = pelicula;
        /*Hago un plusMinutes a la fechaHoraInicio y le sumo los minutos que dura la pelicula , luego podemos añadirle mas
        si queremos contar los trailers , etc... 
        El casteo a long es porque el metodo usa un long asi que ps porsiaca lo puse*/
        this.fechaHoraFin = fechaHoraInicio.plusMinutes((long) pelicula.getDuracion());
        this.dimension = dimension;
        this.precioBase = precioBase;
        this.sala = sala;
        this.categorizable = categorizable;
        this.dimensionable = dimensionable;
    }

    public Long getIdFuncion() {
        return idFuncion;
    }

    public String getDimension() {
        return dimension;
    }

    public float getPrecioBase() {
        return precioBase;
    }

    public Sala getSala() {
        return sala;
    }

    public Dimensionable getDimensionable() {
        return dimensionable;
    }

    public Categorizable getCategorizable() {
        return categorizable;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idFuncion: ").append(idFuncion).append("\n");
        sb.append("FechaHoraInicio: ").append(" " + fechaHoraInicio.getDayOfMonth())
                .append("-" + fechaHoraInicio.getMonthValue()).append("-" + fechaHoraInicio.getYear())
                .append(" " + fechaHoraInicio.getHour()).append(":" + fechaHoraInicio.getMinute()).append("\n");
        sb.append("FechaHoraFin: ").append(" " + fechaHoraFin.getDayOfMonth())
                .append("-" + fechaHoraFin.getMonthValue()).append("-" + fechaHoraFin.getYear())
                .append(" " + fechaHoraFin.getHour()).append(":" + fechaHoraFin.getMinute()).append("\n");
        sb.append("Pelicula: ").append(pelicula.nombre).append("\n");
        return sb.toString();
    }

}
