/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author USUARIO
 */
public class Butaca {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int idButaca;
    private int fila;
    private int columna;
    private boolean discapacitado;
    
    @ManyToOne
    @JoinColumn (name="idSala")
    private Sala sala;

    public Butaca(int fila, int columna, boolean discapacitado, Sala sala) {
        this.fila = fila;
        this.columna = columna;
        this.discapacitado = discapacitado;
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "Butaca: "+idButaca+"\tfila: "+getFila()+"\tcol: "+getColumna()+"\tdiscapacitado: "+isDiscapacitado();
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public boolean isDiscapacitado() {
        return discapacitado;
    }

    public void setDiscapacitado(boolean discapacitado) {
        this.discapacitado = discapacitado;
    }

    public int getFila() {
        return fila;
    }
    public int getColumna() {
        return columna;
    }
    
    public Sala getSala() {
        return sala;
    }

    public int getIdButaca() {
        return idButaca;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

}
