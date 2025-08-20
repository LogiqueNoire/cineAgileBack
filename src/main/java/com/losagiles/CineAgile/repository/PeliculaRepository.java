
package com.losagiles.CineAgile.repository;

import com.losagiles.CineAgile.dto.NombreDTO;
import com.losagiles.CineAgile.dto.PeliculaCarteleraDTO;
import com.losagiles.CineAgile.entidades.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    Optional<Pelicula> findByNombreIgnoreCase(String nombre);

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
        SELECT DISTINCT p
        FROM Pelicula p
        LEFT JOIN FETCH p.genero
    """)
    List<Pelicula> obtenerPeliculasConEstadoEntidades();

    @Query(value = """
        SELECT SUM(e.costo_final) AS totalRecaudado,
               EXTRACT(MONTH FROM f.fecha_hora_inicio) AS mes
        FROM "cine-dev".pelicula p
        JOIN "cine-dev".funcion f ON p.id_pelicula = f.id_pelicula
        JOIN "cine-dev".entrada e ON f.id = e.id_funcion
        WHERE EXTRACT(YEAR FROM f.fecha_hora_inicio) = EXTRACT(YEAR FROM CURRENT_DATE)
        GROUP BY 2
        ORDER BY 2
    """, nativeQuery = true)
    List<Object[]> obtenerVentasMensuales();

    @Query(value = """      
        SELECT SUM(e.costo_final), p.nombre
        FROM "cine-dev".pelicula as p
        JOIN "cine-dev".funcion as f on p.id_pelicula = f.id_pelicula
        JOIN "cine-dev".entrada as e on f.id = e.id_funcion
        WHERE EXTRACT(MONTH FROM f.fecha_hora_inicio) = :mes
        GROUP by 2
        ORDER by 1 DESC
        LIMIT 7
    """, nativeQuery = true)
    List<Object[]> obtenerPeliculasMasTaquillerasDeMes(@Param("mes") int mes);

    @Query(value = """
        SELECT DISTINCT p
        FROM Entrada e
        JOIN e.funcion f
        JOIN f.pelicula p
        WHERE f.fechaHoraInicio BETWEEN :inicioSem AND :finSem
        AND e.estado = 'listo'
    """)
    List<Pelicula> obtenerPeliculasConVentasEnPeriodoTiempo(@Param("inicioSem") LocalDateTime inicioSem,
                                                            @Param("finSem") LocalDateTime finSem);
}
