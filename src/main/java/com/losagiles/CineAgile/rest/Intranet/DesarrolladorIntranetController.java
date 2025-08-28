package com.losagiles.CineAgile.rest.Intranet;

import com.losagiles.CineAgile.services.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/intranet/desarrollador")
public class DesarrolladorIntranetController {
    @Autowired
    private ScriptService scriptService;

    @PostMapping("/poblarBD")
    public ResponseEntity<String> ejecutarScript() {
        try {
            scriptService.poblarBD();
            return ResponseEntity.ok("Script ejecutado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al ejecutar el script: " + e.getMessage());
        }
    }
}
