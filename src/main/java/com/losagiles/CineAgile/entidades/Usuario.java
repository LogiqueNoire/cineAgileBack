package com.losagiles.CineAgile.entidades;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GeneratedColumn;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(name = "username", columnNames = { "username" }))
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String password;
}
