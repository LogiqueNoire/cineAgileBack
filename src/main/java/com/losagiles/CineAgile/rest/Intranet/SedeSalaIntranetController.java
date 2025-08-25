package com.losagiles.CineAgile.rest.Intranet;

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
import java.util.Optional;

@RestController
@RequestMapping("/intranet")
public class SedeSalaIntranetController {
    @Autowired
    SedeService sedeService;

    @Autowired
    SalaService salaService;

    @Autowired
    SalaButacasService salaButacasService;

    @GetMapping("/sedesTodas")
    public List<?> getSedes() {
        return sedeService.getNombresSedes();
    }

    @PostMapping("/sedesysalas/agregar")
    private ResponseEntity<?> addSede(@RequestBody SedeDTO dto) {
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

    @GetMapping ("/soloSedes")
    private ResponseEntity<List<NombreDTO>> getNombresSedesActivas(){
        return ResponseEntity.ok(sedeService.getNombresSedesActivas());
    }

    @PostMapping("/crearsala")
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

    @PatchMapping("/editarSede")
    public ResponseEntity<?> editarSede(@RequestBody NombreDTO sedeDTO){
        Optional<Sede> actualizada = sedeService.editarSede(sedeDTO);

        if (actualizada.isEmpty()) {
            return ResponseEntity.status(409).body("Nombre de sede ya está en uso");
        }

        return actualizada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("activarDesactivarSede")
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

    @GetMapping ("/salasPorSede")
    private ResponseEntity<List<Sala>> getSalasPorSede(@RequestParam Long idSede){
        return ResponseEntity.ok(salaButacasService.getSalasPorSede(idSede));
    }

    @PatchMapping("/sala")
    public ResponseEntity<String> patchSala(@RequestBody SolicitudEditarSala solicitudEditarSala) {
        ResEditarSalaResultCode res = salaService.editarSala(solicitudEditarSala);

        if (res != ResEditarSalaResultCode.NO_ERROR)
            return ResponseEntity.status(422).body(res.getDescripcion());

        return ResponseEntity.ok(res.getDescripcion());
    }

    @PatchMapping("/sala/cambiar-estado/{id}")
    public ResponseEntity<String> patchSalaCambiarEstado(@PathVariable Long id, @RequestBody NombreDTO nombreDTO) {
        Sala sala = salaService.establecerEstadoSala(id, nombreDTO.getActivo());

        if (sala == null)
            return ResponseEntity.status(404).body("Sala no encontrada.");

        return ResponseEntity.ok("Estado de sala " + id + " cambiado a " + nombreDTO.getActivo() + "!");
    }
}
