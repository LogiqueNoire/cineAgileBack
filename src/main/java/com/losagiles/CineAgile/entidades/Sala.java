/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

// La etiqueta @Data es proveida por lombok. Esta es útil
// porque nos ahorra colocar los getters, setters y constructores
@Data
@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(name = "", columnNames = { "id_sede", "codigoSala" })
)
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String codigoSala;

    @Column(nullable = false)
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "id_sede") // este nombre puede variar según tu tabla
    @JsonBackReference
    private Sede sede;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Butaca> butacas;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Funcion> funciones;
}
