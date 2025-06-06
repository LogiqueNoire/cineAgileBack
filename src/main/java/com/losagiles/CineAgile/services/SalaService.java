package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.SolicitudCrearSala;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

    public Sala crearSala(SolicitudCrearSala solicitudCrearSala) {
        try {
            Sala sala = Sala.builder()
                    .codigoSala(solicitudCrearSala.codigoSala())
                    .categoria(solicitudCrearSala.categoria())
                    .butacas(solicitudCrearSala.butacas())
                    .sede(Sede.builder().id(solicitudCrearSala.idSede()).build())
                    .build();
            sala.getButacas().forEach(butaca -> butaca.setSala(sala));

            return salaRepository.save(sala);
        }
        catch (DataIntegrityViolationException e) {
            return null;
        }
    }
}
