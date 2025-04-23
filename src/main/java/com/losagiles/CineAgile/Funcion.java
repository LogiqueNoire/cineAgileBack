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
}
