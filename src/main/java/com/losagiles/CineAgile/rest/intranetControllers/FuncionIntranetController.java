package com.losagiles.CineAgile.rest.intranetControllers;

import com.losagiles.CineAgile.dto.FuncionDTO;
import com.losagiles.CineAgile.entidades.Funcion;
import com.losagiles.CineAgile.services.StrategiaPrecioFuncion.FuncionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/intranet/funciones")
public class FuncionIntranetController {
    @Autowired
    FuncionService funcionService;

    @PatchMapping
    public ResponseEntity<?> patchFuncion(@RequestBody FuncionDTO funcionDTO){
        if(funcionDTO.getIdFuncion() == null ||
                funcionDTO.getFechaHoraInicio() == null ||
                funcionDTO.getDimension () == null ||
                funcionDTO.getPrecioBase() == 0 ||
                funcionDTO.getIdSala() == null ||
                funcionDTO.getIdPelicula() == null){
            return ResponseEntity.status(400).body("Datos incompletos");
        }
        Optional<?> actualizada = funcionService.actualizarFuncion(funcionDTO);
        return actualizada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Función no encontrada"));
    }

    @PostMapping
    public ResponseEntity<?> postFuncion(@RequestBody FuncionDTO funcionDTO){
        if(funcionDTO.getFechaHoraInicio() == null ||
                funcionDTO.getDimension () == null ||
                funcionDTO.getPrecioBase() == 0 ||
                funcionDTO.getIdSala() == null ||
                funcionDTO.getIdPelicula() == null){
            return ResponseEntity.status(400).body("Datos incompletos");
        }
        Optional<Funcion> funcion = funcionService.save(funcionDTO);
        return funcion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    private ResponseEntity<?> getFunciones(@RequestParam String fecha,
                                           @RequestParam String estado){
        LocalDateTime fechaReal = LocalDateTime.parse(fecha.replace("Z", ""));
        if(estado.compareToIgnoreCase("por_proyectar") == 0) {
            Integer resultado = funcionService.funcionesPorProyectar(fechaReal);
            if (resultado != null)
                return ResponseEntity.ok(resultado);
            else
                return ResponseEntity.status(404).body("Error");
        }
        if(estado.compareToIgnoreCase("agotadas") == 0) {
            Integer resultado = funcionService.funcionesAgotadasEnPeriodoTiempo(fechaReal);
            if (resultado != null)
                return ResponseEntity.ok(resultado);
            else
                return ResponseEntity.status(404).body("Error");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos incompletos");
    }

    @GetMapping("/porSemana")
    private ResponseEntity<?> buscarFuncionesPorSemanaConPelicula(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
            @RequestParam Long idSede,
            @RequestParam(required = false) Long pelicula,
            @RequestParam(required = false) Long sala) {
        if(pelicula!=null)
            return ResponseEntity.ok(funcionService.buscarFuncionesPorSemanaConPelicula(fecha, pelicula, idSede));
        if(sala!=null)
            return ResponseEntity.ok(funcionService.buscarFuncionesPorSemanaConSala(fecha, sala, idSede));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan parámetros");

    }
}
