package com.losagiles.CineAgile.controllersIntranet;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.services.UsuarioInternoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intranet/v1/usuarios")
public class UsuarioInternoIntranetController {
    @Autowired
    UsuarioInternoService usuarioInternoService;

    @GetMapping("/perfil")
    public ResponseEntity<String> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated())
            return ResponseEntity.status(401).body("no autenticado");

        return ResponseEntity.ok((String) auth.getPrincipal());
    }

    @GetMapping
    public ResponseEntity<List<UsuarioTablaDTO>> getUsuarios() {
        return ResponseEntity.ok(usuarioInternoService.mostrarUsuariosEnTabla());
    }

    @PostMapping
    public ResponseEntity<String> crearUsuario(@RequestBody SolicitudCrearUsuario solicitudCrearUsuario) {
        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if (res.error() != null) {
            if (res.error() == ResUsuarioErrorCode.NOMBRE_USUARIO_INVALIDO ||
                    res.error() == ResUsuarioErrorCode.CONTRASENA_INVALIDA ||
                    res.error() == ResUsuarioErrorCode.SEDE_NO_EXISTE) {
                return ResponseEntity.status(422).body(res.error().getDescripcion());
            }

            if (res.error() == ResUsuarioErrorCode.USUARIO_YA_EXISTE)
                return ResponseEntity.status(409).body(res.error().getDescripcion());
        }

        return ResponseEntity.status(201).body("¡Usuario creado con exito!");
    }

    @PatchMapping("/nuevacontra")
    public ResponseEntity<String> setNuevaContra(@RequestBody SolicitudCambiarContra solicitudCambiarContra) {
        ResCambiarContraErrorCode res = usuarioInternoService.cambiarContra(solicitudCambiarContra);

        if (res != null) {
            if (res == ResCambiarContraErrorCode.CONTRA_ACTUAL_INVALIDA ||
                    res == ResCambiarContraErrorCode.LONGITUD_INVALIDA ||
                    res == ResCambiarContraErrorCode.FORMATO_INVALIDO)
                return ResponseEntity.status(422).body(res.getDescripcion());
        }

        return ResponseEntity.ok("Se cambió la contraseña.");
    }
}
