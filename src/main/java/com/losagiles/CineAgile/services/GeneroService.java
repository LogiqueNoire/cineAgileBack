package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.NombreDTO;
import com.losagiles.CineAgile.entidades.Genero;
import com.losagiles.CineAgile.otros.Auditable;
import com.losagiles.CineAgile.otros.TipoAccion;
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

    @Auditable(value = TipoAccion.CREAR, nombreEntidad = "Género", detalles = "Crear género")
    public Genero save(String nombre) {
        if(generoRepository.findByNombre(nombre) == null) {
            Genero genero = new Genero();
            genero.setNombre(nombre);
            return generoRepository.save(genero);
        } else {
            return null;
        }
    }

    @Auditable(value = TipoAccion.EDITAR, nombreEntidad = "Género", detalles = "Crear género global")
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

    public List<Genero> findAllById(List<Long> generoIds) {
        return generoRepository.findAllById(generoIds);
    }

    public List<Genero> findAllByPeliculaId(Long peliculaId) {
        return generoRepository.findGenerosByPeliculaId(peliculaId);
    }
}
