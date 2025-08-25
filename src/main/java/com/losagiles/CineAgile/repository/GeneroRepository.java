package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.entidades.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GeneroRepository extends JpaRepository<Genero, Long> {

    public Genero findByNombre(String nombre);

    public List<Genero> findAllByNombre(String nombre);

    @Query("""
        SELECT g 
        FROM Genero g 
        JOIN g.peliculas p 
        WHERE p.id = :peliculaId
    """)
    List<Genero> findGenerosByPeliculaId(@Param("peliculaId") Long peliculaId);
}
