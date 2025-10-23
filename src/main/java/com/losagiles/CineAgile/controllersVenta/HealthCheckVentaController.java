package com.losagiles.CineAgile.controllersVenta;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venta/v1/health")
public class HealthCheckVentaController {

    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Servidor activo");
    }
}