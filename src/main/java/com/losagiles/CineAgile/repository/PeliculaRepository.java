
package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.entidades.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    @Query("""
            SELECT new com.losagiles.CineAgile.dto.PeliculaCarteleraDTO(
                    p.idPelicula,
                    p.nombre,
                    p.estado,
                    p.imageUrl,
                    p.sinopsis
            )
            FROM Pelicula p
            WHERE p.estado = :estado
            """)
    public List<Pelicula> getPeliculasByEstado(@Param("estado") String estado);
}
