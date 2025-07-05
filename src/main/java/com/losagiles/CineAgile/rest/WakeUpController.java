package com.losagiles.CineAgile.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WakeUpController {

    @GetMapping("/wakeup")
    public ResponseEntity<String> ping() {
        System.out.println("Ping recibido para mantener el backend activo");
        return ResponseEntity.ok("Servidor activo");
    }
}
