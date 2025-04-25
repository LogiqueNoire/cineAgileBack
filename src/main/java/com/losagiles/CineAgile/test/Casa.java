package com.losagiles.CineAgile.test;

import jakarta.persistence.*;

@Entity
public class Casa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id_casa")
    Long id;
    String nombre;
    String direccion;

    public Casa() {}

    public Casa(Long idCasa, String nombre, String direccion) {
        this.id = idCasa;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Long getIdCasa() {
        return id;
    }

    public void setIdCasa(Long idCasa) {
        this.id = idCasa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
