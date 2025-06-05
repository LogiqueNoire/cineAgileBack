
package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.dto.NombreDTO;
import com.losagiles.CineAgile.dto.PeliculaCarteleraDTO;
import com.losagiles.CineAgile.entidades.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;

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
    public List<PeliculaCarteleraDTO> getPeliculasByEstado(@Param("estado") String estado);

    @Query("""
            SELECT DISTINCT new com.losagiles.CineAgile.dto.NombreDTO(
                    p.idPelicula,
                    p.nombre
            )
            FROM Pelicula p
            JOIN Funcion f ON f.pelicula.idPelicula = p.idPelicula
            JOIN Sala as sa on f.sala.id = sa.id
            JOIN Sede as se on sa.sede.id = se.id
            WHERE se.id = :idSede
            """)
    public List<NombreDTO> getNombresPeliculas(@Param("idSede") Long idSede);
}
