package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.entidades.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneroRepository extends JpaRepository<Genero, Long> {

    public Genero findByNombre(String nombre);

    public List<Genero> findAllByNombre(String nombre);
}
