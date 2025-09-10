package com.losagiles.CineAgile.rest.intranetControllers;

import com.losagiles.CineAgile.dto.DiaHoraVentaDTO;
import com.losagiles.CineAgile.services.EntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;

@RestController
@RequestMapping("/intranet")
public class EntradaIntranetController {
    @Autowired
    EntradaService entradaService;

    @GetMapping("/getEntradasVendidas")
    private ResponseEntity<?> entradasVendidasEnPeriodoTiempo(@RequestParam String fechaReal){
        LocalDateTime fecha = LocalDateTime.parse(fechaReal.replace("Z", ""));
        Integer resultado = Optional.ofNullable(entradaService.entradasVendidasEnPeriodoTiempo(fecha))
                .orElse(0);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping ("/getVentas")
    private ResponseEntity<?> ventasEnPeriodoTiempo(@RequestParam String fechaReal){
        LocalDateTime fecha = LocalDateTime.parse(fechaReal.replace("Z", ""));
        BigDecimal resultado = Optional.ofNullable(entradaService.ventasEnPeriodoTiempo(fecha))
                .orElse(BigDecimal.ZERO);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/getDesempenoSemanal")
    private ResponseEntity<?> getDesempenoSemanal(@RequestParam Long idPelicula, @RequestParam String fechaReal){
        LocalDateTime fecha = LocalDateTime.parse(fechaReal.replace("Z", ""));
        LinkedList<DiaHoraVentaDTO> resultado = entradaService.getDesempenoSemanal(idPelicula,
                fecha.toLocalDate().atStartOfDay());
        return ResponseEntity.ok(resultado);
    }
}
