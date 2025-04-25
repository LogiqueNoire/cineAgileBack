package com.losagiles.CineAgile.services.rest;

import com.losagiles.CineAgile.Pelicula;
import com.losagiles.CineAgile.services.PeliculaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Pelicula")
public class PeliculaREST {
    @Autowired
    PeliculaService peliculaService;

    @GetMapping
    private ResponseEntity <List<Pelicula>> getAllPeliculas(){
        return ResponseEntity.ok(peliculaService.findAll()); 
    }
    @GetMapping
    private ResponseEntity <Pelicula> getPeliculaById(Long id){
        return ResponseEntity.ok(peliculaService.getById(id)); 
    }
    @GetMapping
    private ResponseEntity<Boolean> PeliculaExiste(Long id){
        return ResponseEntity.ok(peliculaService.existsById(id)); 
    }
    @GetMapping
    private ResponseEntity <Long> contarPeliculas(){
        return ResponseEntity.ok(peliculaService.count()); 
    }
    
    /*
    @DeleteMapping
    private void deletePeliculaById(Long id){
        peliculaService.deleteById(id); 
        
    }
    */
    
    private ResponseEntity<List<Pelicula>> getPeliculasSort(Sort sort){
        return ResponseEntity.ok(peliculaService.findAll(sort)); 
    }
}
