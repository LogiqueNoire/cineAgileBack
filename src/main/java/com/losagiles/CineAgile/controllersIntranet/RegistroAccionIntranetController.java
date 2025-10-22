package com.losagiles.CineAgile.controllersIntranet;

import com.losagiles.CineAgile.entidades.RegistroAccion;
import com.losagiles.CineAgile.services.RegistroAccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/intranet/v1/registrosacciones")
public class RegistroAccionIntranetController {
    @Autowired
    RegistroAccionService registroAccionService;

    @GetMapping
    private ResponseEntity<?> getAllRegistroAccion(){
        List<RegistroAccion> result = registroAccionService.listarAcciones();
        if(result != null){
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al consultar regsitros de auditoría");
        }
    }
}
