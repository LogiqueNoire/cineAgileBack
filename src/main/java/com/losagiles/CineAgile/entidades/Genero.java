package com.losagiles.CineAgile.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Genero")
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column (nullable = false)
    String nombre;

    @JsonIgnore
    @ManyToMany(mappedBy = "genero")
    private List<Pelicula> peliculas;

}
