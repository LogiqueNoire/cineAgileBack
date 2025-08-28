
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.entidades.Genero;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.otros.Auditable;
import com.losagiles.CineAgile.otros.TipoAccion;
import com.losagiles.CineAgile.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {
    @Autowired
    PeliculaRepository peliculaRepository;

    @Autowired
    GeneroService generoService;

    public Pelicula mostrarPelicula(Long idPelicula) {
        Optional<Pelicula> pelicula = peliculaRepository.findById(idPelicula);
        
        return pelicula.orElse(null);
    }

    public List<PeliculaCarteleraDTO> mostrarPeliculasEnCartelera(LocalDateTime fechaDesdeFront) {
        LocalDate hoy = fechaDesdeFront.toLocalDate();
        System.out.println("Fecha desde front"+ hoy);
        return peliculaRepository.getPeliculasEnCartelera(hoy, fechaDesdeFront);
    }

    public List<PeliculaCarteleraDTO> mostrarPeliculasProximamente(LocalDateTime fechaDesdeFront) {
        LocalDate fecha = fechaDesdeFront.toLocalDate();
        return peliculaRepository.getPeliculasProximamente(fecha);
    }

    @Auditable(value = TipoAccion.CREAR, nombreEntidad = "Pelicula", detalles = "Agregar película")
    public Pelicula agregarPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    public List<PeliculaDTO> obtenerPeliculasConEstado(LocalDateTime ahora) {
        System.out.println("ahora"+ahora);
        LocalDate hoy = ahora.toLocalDate();
        System.out.println("hoy"+hoy);
        List<Pelicula> peliculas = peliculaRepository.obtenerPeliculasConEstadoEntidades();

            return peliculas.stream().map(p -> {
                String estado;
                boolean enCartelera = p.getFuncion().stream()
                        .anyMatch(f -> f.getFechaHoraInicio().isAfter(ahora));

                if (!p.getFechaInicioEstreno().isBefore(hoy) && !enCartelera) {
                    estado = "Próximamente";
                } else if (enCartelera) {
                    estado = "En cartelera";
                } else {
                    estado = "Finalizada";
                }

                return new PeliculaDTO(
                        p.getIdPelicula(),
                        p.getNombre(),
                        p.getDirector(),
                        p.getActores(),
                        p.getGenero(),
                        p.getClasificacion(),
                        p.getDuracion(),
                        estado,
                        p.getFechaInicioEstreno().toString(),
                        p.getImageUrl(),
                        p.getSinopsis()
                );
            }).toList();


    }

    public List<Pelicula> findAll() {
        return peliculaRepository.findAll();
    }

    public List<NombreDTO> getNombresPeliculas(Long idSede) { return peliculaRepository.getNombresPeliculas(idSede); }

    @Auditable(value = TipoAccion.EDITAR, nombreEntidad = "Pelicula", detalles = "Editar datos de película")
    @Transactional
    public PatchPeliculaStatus editarPelicula(PatchPeliculaRequest patchPeliculaRequest) {
        try {
            Pelicula pelicula = peliculaRepository.findById(patchPeliculaRequest.idPelicula()).orElse(null);

            if (pelicula != null) {
                if (patchPeliculaRequest.nombre() != null) {
                    if (patchPeliculaRequest.nombre().length() > 255)
                        return PatchPeliculaStatus.SUPERA_LIMITE_CARACTERES;

                    String strippedNombre = patchPeliculaRequest.nombre().strip();
                    Pelicula existente = peliculaRepository.findByNombreIgnoreCase(strippedNombre).orElse(null);
                    if (existente != null) return PatchPeliculaStatus.NOMBRE_REPETIDO;

                    pelicula.setNombre(strippedNombre);
                }

                if (patchPeliculaRequest.director() != null) {
                    if (patchPeliculaRequest.director().length() > 255)
                        return PatchPeliculaStatus.SUPERA_LIMITE_CARACTERES;
                    pelicula.setDirector(patchPeliculaRequest.director());
                }

                if (patchPeliculaRequest.duracion() != null) {
                    if (patchPeliculaRequest.duracion() > 500 || patchPeliculaRequest.duracion() < 1)
                        return PatchPeliculaStatus.DURACION_FUERA_DE_RANGO;
                    pelicula.setDuracion(patchPeliculaRequest.duracion());
                }

                if (patchPeliculaRequest.sinopsis() != null) {
                    if (patchPeliculaRequest.sinopsis().length() > 500)
                        return PatchPeliculaStatus.SUPERA_LIMITE_CARACTERES;
                    pelicula.setSinopsis(patchPeliculaRequest.sinopsis());
                }

                if (patchPeliculaRequest.clasificacion() != null) {
                    pelicula.setClasificacion(patchPeliculaRequest.clasificacion());
                }

                if (patchPeliculaRequest.actores() != null) {
                    if (patchPeliculaRequest.actores().length() > 255)
                        return PatchPeliculaStatus.SUPERA_LIMITE_CARACTERES;
                    pelicula.setActores(patchPeliculaRequest.actores());
                }

                if (patchPeliculaRequest.fechaEstreno() != null) {
                    pelicula.setFechaInicioEstreno(patchPeliculaRequest.fechaEstreno());
                }

                if (patchPeliculaRequest.urlImagen() != null) {
                    if (patchPeliculaRequest.urlImagen().length() > 255)
                        return PatchPeliculaStatus.SUPERA_LIMITE_CARACTERES;
                    pelicula.setImageUrl(patchPeliculaRequest.urlImagen());
                }

                if (patchPeliculaRequest.generos() != null) {
                    List<Long> generosIds = patchPeliculaRequest.generos().stream().map(Genero::getId).toList();
                    List<Genero> generos = generoService.findAllById(generosIds);

                    if (generos.size() != generosIds.size()) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return PatchPeliculaStatus.GENEROS_INVALIDOS;
                    }

                    pelicula.setGenero(patchPeliculaRequest.generos());
                }

                return PatchPeliculaStatus.NO_ERROR;
            }

            return PatchPeliculaStatus.PELICULA_NO_EXISTE;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return PatchPeliculaStatus.ERROR_INTERNO;
        }

    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Varias", detalles = "Consultar ventas de un mes")
    public List<Object[]> obtenerVentasMensuales(){
        return peliculaRepository.obtenerVentasMensuales();
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Varias", detalles = "Consultar películas más taquilleras")
    public List<Object[]> obtenerPeliculasMasTaquillerasDeMes(int mes){
        return peliculaRepository.obtenerPeliculasMasTaquillerasDeMes(mes);
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Entradas", detalles = "Consultar películas y ventas en periodo")
    public List<Pelicula> obtenerPeliculasConVentasEnPeriodoTiempo(LocalDateTime fecha){
        LocalDateTime inicioSemana = fecha.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
        LocalDateTime finSemana = inicioSemana.plusDays(6).toLocalDate().atTime(LocalTime.MAX);
        return peliculaRepository.obtenerPeliculasConVentasEnPeriodoTiempo(inicioSemana, finSemana);
    }
}
