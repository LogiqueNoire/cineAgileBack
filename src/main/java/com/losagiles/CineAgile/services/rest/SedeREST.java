/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services.rest;

import com.losagiles.CineAgile.Sede;
import com.losagiles.CineAgile.services.SedeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author CARDENAS IGLESIAS HUGO AUGUSTO
 */
@RestController
@RequestMapping("/sede/")
public class SedeREST {
    @Autowired
    private SedeService sedeService;
    
    @GetMapping
    private ResponseEntity<List<Sede>> getAllSedes(){
        return ResponseEntity.ok(sedeService.findAll());
    }
}
