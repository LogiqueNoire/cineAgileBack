package com.losagiles.CineAgile.rest.intranetControllers;

import com.losagiles.CineAgile.dto.NombreDTO;
import com.losagiles.CineAgile.dto.PatchPeliculaRequest;
import com.losagiles.CineAgile.dto.PatchPeliculaStatus;
import com.losagiles.CineAgile.dto.PeliculaDTO;
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
@RequestMapping("/intranet")
public class PeliculaIntranetController {
    @Autowired
    PeliculaService peliculaService;

    @Autowired
    PeliculaRepository peliculaRepository;

    @Autowired
    GeneroService generoService;

    @GetMapping("/peliculas")
    public List<PeliculaDTO> obtenerPeliculasConEstado(@RequestParam String fechaReal) {
        LocalDateTime fecha = LocalDateTime.parse(fechaReal.replace("Z", ""));
        System.out.println(fecha);
        return peliculaService.obtenerPeliculasConEstado(fecha);
    }

    @GetMapping("/soloPeliculas")
    public List<Pelicula> findAll() {
        return peliculaService.findAll();
    }

    @GetMapping ("/peliculasPorSede")
    private ResponseEntity<List<NombreDTO>> getNombresPeliculas(@RequestParam Long idSede){
        return ResponseEntity.ok(peliculaService.getNombresPeliculas(idSede));
    }

    @PostMapping("/peliculas/agregar")
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

    @PatchMapping("/pelicula")
    public ResponseEntity<String> patchPelicula(@RequestBody PatchPeliculaRequest patchPeliculaRequest) {
        PatchPeliculaStatus status = peliculaService.editarPelicula(patchPeliculaRequest);
        return ResponseEntity.status(status.getHttpStatus()).body(status.getDescripcion());
    }

    @GetMapping ("/obtenerPeliculasMasTaquillerasDeMes")
    private ResponseEntity<?> obtenerPeliculasMasTaquillerasDeMes(@RequestParam int mes){
        List<Object[]> resultado = peliculaService.obtenerPeliculasMasTaquillerasDeMes(mes);
        if (resultado != null)
            return ResponseEntity.ok(resultado);
        else
            return ResponseEntity.status(404).body("Error");
    }

    @GetMapping("/getPeliculasConVentas")
    private ResponseEntity<?> obtenerPeliculasConVentasEnPeriodoTiempo(@RequestParam String fechaReal){
        LocalDateTime fecha = LocalDateTime.parse(fechaReal.replace("Z", ""));
        List<Pelicula> resultado = peliculaService.obtenerPeliculasConVentasEnPeriodoTiempo(fecha.toLocalDate().atStartOfDay());
        return ResponseEntity.ok(resultado);
    }

    @GetMapping ("/obtenerVentasMensuales")
    private ResponseEntity<?> obtenerVentasMensuales(){
        List<Object[]> resultado = peliculaService.obtenerVentasMensuales();
        if (resultado != null)
            return ResponseEntity.ok(resultado);
        else
            return ResponseEntity.status(404).body("Error");
    }

}
