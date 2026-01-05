package com.losagiles.CineAgile.controllersIntranet;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.dto.entidadesParciales.SedeDTO;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.otros.Auditable;
import com.losagiles.CineAgile.otros.TipoAccion;
import com.losagiles.CineAgile.services.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/intranet/v1/sedes")
public class SedeIntranetController {
    @Autowired
    SedeService sedeService;

    @PostMapping
    public ResponseEntity<?> addSede(@RequestBody SedeDTO dto) {
        if (sedeService.existsByNombre(dto.getNombre())) {
            return ResponseEntity.status(409).body("Sede repetida");
        } else {
            Sede sede = new Sede();
            sede.setNombre(dto.getNombre());
            sede.setActivo(true);
            sedeService.save(sede);

            return ResponseEntity.ok(sede);
        }
    }

    @GetMapping
    public List<?> getSedes() {
        return sedeService.getNombresSedes();
    }

    @GetMapping ("/activas")
    public ResponseEntity<List<NombreDTO>> getNombresSedesActivas(){
        return ResponseEntity.ok(sedeService.getNombresSedesActivas());
    }

    @PatchMapping
    public ResponseEntity<?> patchSede(@RequestBody NombreDTO sedeDTO){
        Optional<Sede> actualizada = sedeService.editarSede(sedeDTO);

        if (actualizada.isEmpty()) {
            return ResponseEntity.status(409).body("Nombre de sede ya está en uso");
        }

        return actualizada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Auditable(value = TipoAccion.ALTERNAR_ESTADO, nombreEntidad = "Sede", detalles = "Alternar estado de sede")
    @PatchMapping("/estado")
    public ResponseEntity<?> activarDesactivar(@RequestBody NombreDTO dto){
        Sede sede = sedeService.findById(dto.getId()).get();
        if (sede != null) {
            sede.setActivo(!sede.getActivo());
            sedeService.save(sede);
            return ResponseEntity.ok("Sede cambiada");
        } else {
            return ResponseEntity.status(422).body("Error al editar");
        }
    }

}
