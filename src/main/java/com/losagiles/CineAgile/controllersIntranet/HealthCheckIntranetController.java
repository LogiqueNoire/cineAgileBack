package com.losagiles.CineAgile.controllersIntranet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/intranet/v1/health")
public class HealthCheckIntranetController {

    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Servidor activo");
    }
}