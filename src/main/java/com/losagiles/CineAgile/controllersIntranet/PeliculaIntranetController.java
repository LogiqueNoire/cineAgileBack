package com.losagiles.CineAgile.controllersIntranet;

import com.losagiles.CineAgile.dto.PatchPeliculaRequest;
import com.losagiles.CineAgile.dto.PatchPeliculaStatus;
import com.losagiles.CineAgile.dto.entidadesParciales.PeliculaDTO;
import com.losagiles.CineAgile.entidades.Genero;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.repository.PeliculaRepository;
import com.losagiles.CineAgile.services.GeneroService;
import com.losagiles.CineAgile.services.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/intranet/v1/peliculas")
public class PeliculaIntranetController {
    @Autowired
    PeliculaService peliculaService;

    @Autowired
    PeliculaRepository peliculaRepository;

    @Autowired
    GeneroService generoService;

    @GetMapping
    public ResponseEntity<List<?>> obtenerPeliculasConEstado(@RequestParam(required = false) String fecha,
                                                             @RequestParam(required = false) Long idSede) {

        //Caso 1: devolver con estado usando fecha /peliculas?idSede=
        if (idSede != null){
            return ResponseEntity.ok(peliculaService.getNombresPeliculas(idSede));
        }
        //Caso 2: devolver con estado usando fecha /peliculas?fecha=
        if(fecha != null) {
            LocalDateTime fechaReal = LocalDateTime.parse(fecha.replace("Z", ""));
            System.out.println(fechaReal);
            return ResponseEntity.ok(peliculaService.obtenerPeliculasConEstado(fechaReal));
        }
        //Caso 3: todas las peliculas /peliculas
        return ResponseEntity.ok(peliculaService.findAll());
    }

    @PostMapping
    private ResponseEntity<Pelicula> addPelicula(@RequestBody PeliculaDTO dto) {
        Pelicula pelicula = new Pelicula();

        Pelicula existente = peliculaRepository.findByNombreIgnoreCase(dto.getNombre().strip()).orElse(null);
        if (existente != null) return ResponseEntity.status(409).body(null);

        pelicula.setNombre(dto.getNombre());
        pelicula.setDirector(dto.getDirector());
        pelicula.setActores(dto.getActores());

        List<Long> generoIds = dto.getGenero().stream()
                .map(Genero::getId)
                .collect(Collectors.toList());

        List<Genero> generos = generoService.findAllById(generoIds);
        pelicula.setGenero(generos);

        pelicula.setClasificacion(dto.getClasificacion());
        pelicula.setDuracion(dto.getDuracion());

        LocalDate fechaEstreno = LocalDate.parse(dto.getFechaInicioEstreno());
        pelicula.setFechaInicioEstreno(fechaEstreno);

        pelicula.setImageUrl(dto.getImageUrl());
        pelicula.setSinopsis(dto.getSinopsis());

        peliculaService.agregarPelicula(pelicula);
        return ResponseEntity.ok(pelicula);
    }

    @PatchMapping
    public ResponseEntity<String> patchPelicula(@RequestBody PatchPeliculaRequest patchPeliculaRequest) {
        PatchPeliculaStatus status = peliculaService.editarPelicula(patchPeliculaRequest);
        return ResponseEntity.status(status.getHttpStatus()).body(status.getDescripcion());
    }

    @GetMapping ("/taquilleras")
    private ResponseEntity<?> obtenerPeliculasMasTaquillerasDeMes(@RequestParam int mes){
        List<Object[]> resultado = peliculaService.obtenerPeliculasMasTaquillerasDeMes(mes);
        if (resultado != null)
            return ResponseEntity.ok(resultado);
        else
            return ResponseEntity.status(404).body("Error");
    }

    @GetMapping("/ventas")
    private ResponseEntity<?> obtenerPeliculasConVentasEnPeriodoTiempo(@RequestParam String fecha){
        LocalDateTime fechaReal = LocalDateTime.parse(fecha.replace("Z", ""));
        List<Pelicula> resultado = peliculaService.obtenerPeliculasConVentasEnPeriodoTiempo(fechaReal.toLocalDate().atStartOfDay());
        return ResponseEntity.ok(resultado);
    }

}
