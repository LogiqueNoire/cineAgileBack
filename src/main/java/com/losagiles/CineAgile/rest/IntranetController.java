package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.entidades.Funcion;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/intranet")
public class IntranetController {
    @Autowired
    PeliculaService peliculaService;

    @Autowired
    SedeService sedeService;

    @Autowired
    SalaButacasService salaButacasService;

    @Autowired
    FuncionService funcionService;

    @Autowired
    SalaService salaService;

    @GetMapping("/peliculas")
    public List<Pelicula> findAll() {
        return peliculaService.findAll();
    }

    @PostMapping("/peliculas/agregar")
    private ResponseEntity<Pelicula> addPelicula(@RequestBody PeliculaDTO dto) {
        Pelicula pelicula = new Pelicula();

        pelicula.setNombre(dto.getNombre());
        pelicula.setDirector(dto.getDirector());
        pelicula.setActores(dto.getActores());
        pelicula.setGenero(dto.getGenero());
        pelicula.setClasificacion(dto.getClasificacion());
        pelicula.setDuracion(dto.getDuracion());
        pelicula.setEstado(dto.getEstado());

        LocalDate fechaEstreno = LocalDate.parse(dto.getFechaInicioEstreno());
        pelicula.setFechaInicioEstreno(fechaEstreno);

        pelicula.setImageUrl(dto.getImageUrl());
        pelicula.setSinopsis(dto.getSinopsis());

        peliculaService.agregarPelicula(pelicula);
        return ResponseEntity.ok(pelicula);
    }

    @GetMapping("/sedesysalas")
    public List<Sede> getSedes() {
        return sedeService.findAll();
    }

    @PostMapping("/sedesysalas/agregar")
    private ResponseEntity<Sede> addSede(@RequestBody SedeDTO dto) {
        Sede sede = new Sede();
        sede.setNombre(dto.getNombre());
        sedeService.agregarSede(sede);

        return ResponseEntity.ok(sede);
    }

    @PostMapping("/sedesysalas/nuevaSala")
    public ResponseEntity<Sala> agregarSala(@RequestBody SalaDTO salaDTO) {
        Optional<Sede> sedeOptional = sedeService.findById(salaDTO.getIdSede());

        Sede sede = sedeOptional.get();
        System.out.println("Sede agregada: " + sede.getId());

        Sala sala = new Sala();
        sala.setCodigoSala(salaDTO.getCodigoSala());
        sala.setCategoria(salaDTO.getCategoria());
        sala.setSede(sede);

        Sala guardada = salaButacasService.save(sala);

        return ResponseEntity.ok(guardada);
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated())
            return ResponseEntity.status(401).body("no autenticado");

        return ResponseEntity.ok((String) auth.getPrincipal());
    }

    @GetMapping ("/soloSedes")
    private ResponseEntity<List<NombreDTO>> getNombresSedes(){
        return ResponseEntity.ok(sedeService.getNombresSedes());
    }

    @GetMapping ("/peliculasPorSede")
    private ResponseEntity<List<NombreDTO>> getNombresPeliculas(@RequestParam Long idSede){
        return ResponseEntity.ok(peliculaService.getNombresPeliculas(idSede));
    }

    @GetMapping ("/salasPorSede")
    private ResponseEntity<List<Sala>> getSalasPorSede(@RequestParam Long idSede){
        return ResponseEntity.ok(salaButacasService.getSalasPorSede(idSede));
    }

    @GetMapping ("/buscarFuncionesPorSemanaConPelicula")
    private ResponseEntity<List<FuncionDTO>>
    buscarFuncionesPorSemanaConPelicula(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
            @RequestParam Long idPelicula,
            @RequestParam Long idSede) {
        return ResponseEntity.ok(funcionService.buscarFuncionesPorSemanaConPelicula(fecha, idPelicula, idSede));
    }

    @GetMapping ("/buscarFuncionesPorSemanaConSala")
    private ResponseEntity<List<FuncionDTO>>
    buscarFuncionesPorSemanaConSala(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
            @RequestParam Long idSala,
            @RequestParam Long idSede){
        return ResponseEntity.ok(funcionService.buscarFuncionesPorSemanaConSala(fecha, idSala, idSede));
    }


    @PostMapping("/crearsala")
    public ResponseEntity<String> crearSala(@RequestBody SolicitudCrearSala solicitudCrearSala) {
        ResCrearSala resSala = salaService.crearSala(solicitudCrearSala);

        if (resSala.error() != null) {
            if (resSala.error() == ResSalaErrorCode.EXCEPCION_INTEGRACION_DATOS)
                return ResponseEntity
                        .status(409)
                        .body(resSala.error().getDescripcion());

            if (resSala.error() == ResSalaErrorCode.MAX_FILA_SOBREPASADA
                    || resSala.error() == ResSalaErrorCode.BUTACAS_NO_ENCONTRADAS) {
                return ResponseEntity
                        .status(422)
                        .body(resSala.error().getDescripcion());
            }
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/actualizarFuncion")
    public ResponseEntity<Funcion> actualizarFuncion(@RequestBody FuncionDTO funcionDTO){
        Optional<Funcion> actualizado = funcionService.actualizarFuncion(funcionDTO);
        return actualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
