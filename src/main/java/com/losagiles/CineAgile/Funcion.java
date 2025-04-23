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
    Date horaInicio;
    @Column
    Date horaFin;
    
    @Column
    String dimension;
    @Column
    float precioBase;
    @Column
    Sala sala;
        
    Categorizable categorizable;
    Dimensionable dimensionable;
    Personeable personeable;
    
    
    public float precios(){
	return getPrecioBase()
	+categorizable.precio(precioBase)
        +dimensionable.precio()
        +personeable.precio(precioBase);
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

    public Categorizable getCategorizable() {
        return categorizable;
    }
  

}
