
package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.dto.NombreDTO;
import com.losagiles.CineAgile.dto.PeliculaCarteleraDTO;
import com.losagiles.CineAgile.dto.PeliculaDTO;
import com.losagiles.CineAgile.entidades.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    @Query("""
        SELECT new com.losagiles.CineAgile.dto.PeliculaCarteleraDTO(
            p.idPelicula,
            p.nombre,
            p.imageUrl,
            p.sinopsis
        )
        FROM Pelicula p
        WHERE p.fechaInicioEstreno <= :hoy
        AND EXISTS (
            SELECT f FROM Funcion f
            WHERE f.pelicula = p
            AND f.fechaHoraInicio >= :ahora
        )
    """)
    List<PeliculaCarteleraDTO> getPeliculasEnCartelera(
            @Param("hoy") LocalDate hoy,
            @Param("ahora") LocalDateTime ahora
    );

    /*
    SELECT new com.losagiles.CineAgile.dto.PeliculaCarteleraDTO(
            p.idPelicula,
            p.nombre,
            p.imageUrl,
            p.sinopsis
        )
        FROM Pelicula p
        WHERE EXISTS (
            SELECT f FROM Funcion f
            WHERE f.pelicula.idPelicula = p.idPelicula
            AND f.fechaHoraInicio >= :inicio
            AND f.fechaHoraInicio < :fin
    */


    @Query("""
            SELECT new com.losagiles.CineAgile.dto.PeliculaCarteleraDTO(
                    p.idPelicula,
                    p.nombre,
                    p.imageUrl,
                    p.sinopsis
            )
            FROM Pelicula p
            WHERE p.fechaInicioEstreno > :fecha
            """)
    public List<PeliculaCarteleraDTO> getPeliculasProximamente(@Param("fecha") LocalDate fecha);
    /*WHERE NOT EXISTS (
                    SELECT f
                    FROM Funcion f
                    WHERE f.pelicula.idPelicula = p.idPelicula
                    AND f.fechaHoraInicio < :fecha
                )*/



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

    @Query("""
        SELECT new com.losagiles.CineAgile.dto.PeliculaDTO(
            p.nombre,
            p.director,
            p.actores,
            p.genero,
            p.clasificacion,
            p.duracion,
            CASE
                WHEN p.fechaInicioEstreno > :hoy THEN 'Próximamente'
                WHEN EXISTS (
                    SELECT f FROM Funcion f
                    WHERE f.pelicula = p AND f.fechaHoraInicio >= :ahora
                ) THEN 'En cartelera'
                ELSE 'Finalizada'
            END,
            CAST(p.fechaInicioEstreno AS string),
            p.imageUrl,
            p.sinopsis
        )
        FROM Pelicula p
    """)
    List<PeliculaDTO> obtenerPeliculasConEstado(
            @Param("hoy") LocalDate hoy,
            @Param("ahora") LocalDateTime ahora
    );

}
