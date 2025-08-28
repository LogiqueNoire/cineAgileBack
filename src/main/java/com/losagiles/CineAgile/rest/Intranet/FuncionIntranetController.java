package com.losagiles.CineAgile.rest.Intranet;

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
@RequestMapping("/intranet")
public class FuncionIntranetController {
    @Autowired
    FuncionService funcionService;

    @GetMapping("/buscarFuncionesPorSemanaConPelicula")
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

    @PatchMapping("/actualizarFuncion")
    public ResponseEntity<?> actualizarFuncion(@RequestBody FuncionDTO funcionDTO){
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

    @PostMapping("/crearFuncion")
    public ResponseEntity<?> save(@RequestBody FuncionDTO funcionDTO){
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

    @GetMapping ("/getFuncionesPorProyectar")
    private ResponseEntity<?> getFuncionesPorProyectar(@RequestParam String fechaReal){
        LocalDateTime fecha = LocalDateTime.parse(fechaReal.replace("Z", ""));
        Integer resultado = funcionService.funcionesPorProyectar(fecha);
        if (resultado != null)
            return ResponseEntity.ok(resultado);
        else
            return ResponseEntity.status(404).body("Error");
    }

    @GetMapping ("/getFuncionesAgotadas")
    private ResponseEntity<?> getFuncionesAgotadas(@RequestParam String fechaReal){
        LocalDateTime fecha = LocalDateTime.parse(fechaReal.replace("Z", ""));
        Integer resultado = funcionService.funcionesAgotadasEnPeriodoTiempo(fecha);
        if (resultado != null)
            return ResponseEntity.ok(resultado);
        else
            return ResponseEntity.status(404).body("Error");
    }
}
