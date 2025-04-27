
package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.entidades.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    public List<Pelicula> getPeliculasByEstado(String estado);
}
