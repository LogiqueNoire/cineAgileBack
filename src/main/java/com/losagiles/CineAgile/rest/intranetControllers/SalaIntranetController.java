package com.losagiles.CineAgile.rest.intranetControllers;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.services.SalaButacasService;
import com.losagiles.CineAgile.services.SalaService;
import com.losagiles.CineAgile.services.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/intranet/salas")
public class SalaIntranetController {
    @Autowired
    SalaService salaService;

    @Autowired
    SalaButacasService salaButacasService;

    @Autowired
    SedeService sedeService;

    @GetMapping
    public ResponseEntity<List<Sala>> getSalasPorSede(@RequestParam Long idSede){
        return ResponseEntity.ok(salaButacasService.getSalasPorSede(idSede));
    }

    @PatchMapping
    public ResponseEntity<String> patchSala(@RequestBody SolicitudEditarSala solicitudEditarSala) {
        ResEditarSalaResultCode res = salaService.editarSala(solicitudEditarSala);

        if (res != ResEditarSalaResultCode.NO_ERROR)
            return ResponseEntity.status(422).body(res.getDescripcion());

        return ResponseEntity.ok(res.getDescripcion());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<String> patchSalaCambiarEstado(@PathVariable Long id, @RequestBody NombreDTO nombreDTO) {
        Sala sala = salaService.establecerEstadoSala(id, nombreDTO.getActivo());

        if (sala == null)
            return ResponseEntity.status(404).body("Sala no encontrada.");

        return ResponseEntity.ok("Estado de sala " + id + " cambiado a " + nombreDTO.getActivo() + "!");
    }

    @PostMapping
    public ResponseEntity<String> crearSala(@RequestBody SolicitudCrearSala solicitudCrearSala) {
        Sede sede = sedeService.findById(solicitudCrearSala.idSede()).get();
        if(salaService.existsBySedeAndCodigoSala(sede, solicitudCrearSala.codigoSala())){
            return ResponseEntity.status(409).body("Sala repetida");
        } else {

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
    }
}
