package com.losagiles.CineAgile.controllersTiempo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tiempo/v1/wakeup")
public class WakeUpController {

    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Servidor activo");
    }
}
