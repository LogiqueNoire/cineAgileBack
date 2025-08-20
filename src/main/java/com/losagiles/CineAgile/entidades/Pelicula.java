/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table (name = "Pelicula")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long idPelicula;

    @Column (nullable = false)
    String nombre;

    @Column (nullable = false)
    private int duracion; // En minutos

    @Column(length = 500, nullable = false)
    String sinopsis;

    @Column (nullable = false)
    String director;

    @Column (nullable = false)
    String clasificacion;

    @Column (nullable = false)
    String actores;

    @Column (nullable = false)
    @Temporal(TemporalType.DATE)
    LocalDate fechaInicioEstreno;

    @Transient
    String estado;

    @Column (nullable = false)
    String imageUrl;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonManagedReference
    private List<Funcion> funcion;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pelicula_genero",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genero> genero;
}
