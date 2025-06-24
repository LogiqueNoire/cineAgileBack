package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.NombreDTO;
import com.losagiles.CineAgile.entidades.Genero;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroService {
    @Autowired
    GeneroRepository generoRepository;

    public List<Genero> findAll(){
        return generoRepository.findAll();
    }

    public Genero save(String nombre) {
        if(generoRepository.findByNombre(nombre) == null) {
            Genero genero = new Genero();
            genero.setNombre(nombre);
            return generoRepository.save(genero);
        } else {
            return null;
        }
    }

    public Genero editar(NombreDTO dto) {
        List<Genero> otras = generoRepository.findAllByNombre(dto.getNombre());
        System.out.println(dto.getId());
        Genero genero = generoRepository.findById(dto.getId()).get();
        boolean repetido = false;
        for(Genero g : otras){
            if (!g.getId().equals(genero.getId())){
                repetido = true;
            }
        }

        if(!repetido){
            genero.setNombre(dto.getNombre());
            return generoRepository.save(genero);
        }
        return null;
    }
}
