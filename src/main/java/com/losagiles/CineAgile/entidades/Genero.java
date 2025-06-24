package com.losagiles.CineAgile.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Genero")
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column (nullable = false)
    String nombre;

    @ManyToMany(mappedBy = "genero")
    private List<Pelicula> peliculas;

}
