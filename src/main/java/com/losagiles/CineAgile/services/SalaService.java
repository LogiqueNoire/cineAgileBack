package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.ResSalaErrorCode;
import com.losagiles.CineAgile.dto.SolicitudCrearSala;
import com.losagiles.CineAgile.entidades.Butaca;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.losagiles.CineAgile.dto.ResCrearSala;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

    public ResCrearSala crearSala(SolicitudCrearSala solicitudCrearSala) {
        try {
            Sala sala = Sala.builder()
                    .codigoSala(solicitudCrearSala.codigoSala())
                    .categoria(solicitudCrearSala.categoria())
                    .butacas(solicitudCrearSala.butacas())
                    .sede(Sede.builder().id(solicitudCrearSala.idSede()).build())
                    .build();

            if (sala.getButacas().isEmpty())
                return ResCrearSala.builder()
                        .error(ResSalaErrorCode.BUTACAS_NO_ENCONTRADAS)
                        .build();

            for (int i = 0; i < sala.getButacas().size(); i++) {
                Butaca butaca = sala.getButacas().get(i);
                butaca.setSala(sala);

                if (butaca.getFila() > 25) {
                    return ResCrearSala.builder()
                            .error(ResSalaErrorCode.MAX_FILA_SOBREPASADA)
                            .build();
                }
            }

            return ResCrearSala.builder()
                    .sala(salaRepository.save(sala))
                    .build();
        }
        catch (DataIntegrityViolationException e) {
            return ResCrearSala.builder()
                    .error(ResSalaErrorCode.EXCEPCION_INTEGRACION_DATOS)
                    .build();
        }
    }

    public boolean existsBySedeAndCodigoSala(Sede sede, String codigoSala){
        return salaRepository.existsBySedeAndCodigoSala(sede, codigoSala);
    }
}
