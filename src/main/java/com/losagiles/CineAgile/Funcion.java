/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile;

import jakarta.persistence.Entity;
import java.util.Date;

/**
 *
 * @author USUARIO
 */
@Entity
public class Funcion {
    
    int idFuncion;
    Date horaInicio;
    Date horaFin;
    
    String dimension;
    float precioBase;
    Sala sala;

    Dimensionable dimensionable;
    Personeable personeable;
    Categoria categorizable;
    
    public double precios(){
	return getPrecioBase();
/*
        +personable.precio()
	+categorizable.precio()
        +sala.getSala()*/
    }

    public int getIdFuncion() {
        return idFuncion;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
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

    public Personeable getPersoneable() {
        return personeable;
    }

    public Categoria getCategorizable() {
        return categorizable;
    }
    
    
    
    
    
}
