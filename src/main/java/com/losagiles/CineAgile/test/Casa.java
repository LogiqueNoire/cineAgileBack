package com.losagiles.CineAgile.test;

import jakarta.persistence.*;

@Entity
public class Casa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id_casa")
    Long idCasa;

    String nombre;
}
