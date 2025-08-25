package com.losagiles.CineAgile.rest.Intranet;

import com.losagiles.CineAgile.dto.NombreDTO;
import com.losagiles.CineAgile.entidades.Genero;
import com.losagiles.CineAgile.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/intranet")
public class GeneroIntranetController {
    @Autowired
    GeneroService generoService;

    @GetMapping("/generos")
    public ResponseEntity<List<Genero>> getGeneros(){
        return ResponseEntity.ok(generoService.findAll());
    }

    @PostMapping("/generos/agregar")
    public ResponseEntity<?> saveGenero(@RequestBody String nombre){
        Genero g = generoService.save(nombre);
        if(g != null){
            return ResponseEntity.ok(g);
        }
        return ResponseEntity.status(422).body("Error al guardar");
    }

    @PatchMapping("/generos/editar")
    public ResponseEntity<?> editarGenero(@RequestBody NombreDTO dto){
        Genero g = generoService.editar(dto);
        if(g != null){
            return ResponseEntity.ok(g);
        }
        return ResponseEntity.status(422).body("Error al guardar");
    }
}
