package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.SalaDTO;
import com.losagiles.CineAgile.entidades.Butaca;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.repository.ButacaRepository;
import com.losagiles.CineAgile.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalaButacasService {
    @Autowired
    SalaRepository salaRepository;
    @Autowired
    ButacaRepository butacaRepository;

    public Sala mostrarSala(Long idSala) {
        Sala sala = salaRepository.findById(idSala).orElse(null);

        if (sala != null) {
            sala.getButacas().forEach(el -> el.setSala(null));
        }

        return sala;
    }

    public List<Butaca> mostrarButacas(Long idSala) {
        List<Butaca> butacas = butacaRepository.findAllBySalaId(idSala);
        return butacas;
    }

    public Sala save(Sala sala){
        return salaRepository.save(sala);
    }

    public List<Sala> getSalasPorSede(Long idSede){
        return salaRepository.findAllBySede_Id(idSede);
    }

}
