package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.entidades.Butaca;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.repository.SalaRepository;
import com.losagiles.CineAgile.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SedeRepository sedeRepository;

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

    public ResEditarSalaResultCode editarSala(SolicitudEditarSala solicitudEditarSala) {
        String categoria = solicitudEditarSala.categoria().toLowerCase(Locale.ROOT);

        if (solicitudEditarSala.codigoSala().isBlank())
            return ResEditarSalaResultCode.CODIGO_VACIO;

        if (!categoria.equals("regular") && !categoria.equals("prime"))
            return ResEditarSalaResultCode.CATEGORIA_INVALIDA;

        Sala sala = salaRepository.findById(solicitudEditarSala.idSala()).orElse(null);

        if (sala == null)
            return ResEditarSalaResultCode.SALA_INVALIDA;

        if (!sala.getCodigoSala().equals(solicitudEditarSala.codigoSala()) &&
                salaRepository.existsBySedeAndCodigoSala(sala.getSede(), solicitudEditarSala.codigoSala()))
            return ResEditarSalaResultCode.CODIGO_INVALIDO;

        sala.setCodigoSala(solicitudEditarSala.codigoSala());
        sala.setCategoria(solicitudEditarSala.categoria());
        salaRepository.save(sala);

        return ResEditarSalaResultCode.NO_ERROR;
    }

    public boolean existsBySedeAndCodigoSala(Sede sede, String codigoSala){
        return salaRepository.existsBySedeAndCodigoSala(sede, codigoSala);
    }
}
