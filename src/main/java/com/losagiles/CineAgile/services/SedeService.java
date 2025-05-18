/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.entidades.Butaca;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author CARDENAS IGLESIAS HUGO AUGUSTO
 */
@Service
public class SedeService {
    @Autowired
    private SedeRepository sedeRepository;

    public List<Sala> mostrarSalasDeSede(Long idSede) {
        Optional<Sede> sede = sedeRepository.findById(idSede);
        if (sede.isPresent()) {
            return sede.get().getSalas();
        }
        return new ArrayList<>();
    }

    public Sede agregarSede(Sede sede) {
        return sedeRepository.save(sede);
    }
}
