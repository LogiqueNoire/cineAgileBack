/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.NombreDTO;
import com.losagiles.CineAgile.dto.SedeDTO;
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

    public Sede save(Sede sede) {
        return sedeRepository.save(sede);
    }

    public List<Sede> findAll() { return sedeRepository.findAll(); }

    public Optional<Sede> findById(Long idSede){
        return sedeRepository.findById(idSede);
    }

    public List<NombreDTO> getNombresSedes(){
        return sedeRepository.getNombresSedes();
    }

    public boolean existsByNombre(String nombre){ return sedeRepository.existsByNombre(nombre); }

    public Optional<Sede> editarSede(NombreDTO dto){
        List<Sede> otras = sedeRepository.findByNombre(dto.getNombre());
        System.out.println(dto.getId());
        Sede sede = sedeRepository.findById(dto.getId()).get();
        boolean repetido = false;
        for(Sede s : otras){
            if (!s.getId().equals(sede.getId())){
                repetido = true;
            }
        }
        if(repetido){
            return Optional.empty();
        } else {
            sede.setNombre(dto.getNombre());
            return Optional.of(sedeRepository.save(sede));
        }
    }


}
