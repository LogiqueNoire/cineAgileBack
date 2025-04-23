/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.Date;

/**
 *
 * @author USUARIO
 */
@Entity
public class Funcion {
    
    int idFuncion;
    @Column
    Date fechaHoraInicio;
    @Column
    Date fechaHoraFin;
    
    @Column
    String dimension;
    @Column
    float precioBase;
    @Column
    Sala sala;
        
    Categorizable categorizable;
    Dimensionable dimensionable;

    public Funcion(int idFuncion, Date fechaHoraInicio, Date fechaHoraFin, String dimension, float precioBase,
            Sala sala, Categorizable categorizable, Dimensionable dimensionable) {
        this.idFuncion = idFuncion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.dimension = dimension;
        this.precioBase = precioBase;
        this.sala = sala;
        this.categorizable = categorizable;
        this.dimensionable = dimensionable;
    }
    
    public float precio(Personeable personeable){
	return personeable.precio(
                getPrecioBase()
                +categorizable.precio(precioBase)
                +dimensionable.precio()
        );
    }

    public int getIdFuncion() {
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
    
    public Date getFechaHoraFin() {
        return fechaHoraFin;
    }

    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }  

}
