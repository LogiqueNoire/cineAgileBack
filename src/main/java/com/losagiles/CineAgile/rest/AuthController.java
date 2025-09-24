package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.dto.RespuestaLogin;
import com.losagiles.CineAgile.dto.SolicitudLogin;
import com.losagiles.CineAgile.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<RespuestaLogin> getLogin(@RequestBody SolicitudLogin solicitudLogin) {
        if (solicitudLogin == null || solicitudLogin.username() == null || solicitudLogin.password() == null) {
            return ResponseEntity.badRequest().build();
        }

        RespuestaLogin respuesta = authService.login(solicitudLogin);

        if (respuesta.error() != null)
            return ResponseEntity.status(401).body(respuesta);

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/test")
    public ResponseEntity<Void> getTest() {
        return ResponseEntity.ok().build();
    }
}
