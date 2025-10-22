package com.losagiles.CineAgile.controllersIntranet;

import com.losagiles.CineAgile.dto.DiaHoraVentaDTO;
import com.losagiles.CineAgile.services.EntradaService;
import com.losagiles.CineAgile.services.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/intranet/v1/ventas")
public class VentasIntranetController {
    @Autowired
    EntradaService entradaService;

    @Autowired
    PeliculaService peliculaService;

    @GetMapping("/entradas-vendidas")
    private ResponseEntity<?> entradasVendidasEnPeriodoTiempo(@RequestParam String fecha){
        LocalDateTime fechaReal = LocalDateTime.parse(fecha.replace("Z", ""));
        Integer resultado = Optional.ofNullable(entradaService.entradasVendidasEnPeriodoTiempo(fechaReal))
                .orElse(0);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping ("/totales-periodo")
    private ResponseEntity<?> ventasEnPeriodoTiempo(@RequestParam String fecha){
        LocalDateTime fechaReal = LocalDateTime.parse(fecha.replace("Z", ""));
        BigDecimal resultado = Optional.ofNullable(entradaService.ventasEnPeriodoTiempo(fechaReal))
                .orElse(BigDecimal.ZERO);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/desempeno-semanal")
    private ResponseEntity<?> getDesempenoSemanal(@RequestParam Long idPelicula, @RequestParam String fecha){
        LocalDateTime fechaReal = LocalDateTime.parse(fecha.replace("Z", ""));
        LinkedList<DiaHoraVentaDTO> resultado = entradaService.getDesempenoSemanal(idPelicula,
                fechaReal.toLocalDate().atStartOfDay());
        return ResponseEntity.ok(resultado);
    }

    @GetMapping ("/totales-mes")
    private ResponseEntity<?> obtenerVentasMensuales(){
        List<Object[]> resultado = peliculaService.obtenerVentasMensuales();
        if (resultado != null)
            return ResponseEntity.ok(resultado);
        else
            return ResponseEntity.status(404).body("Error");
    }
}
