package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.entidades.Butaca;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.otros.Auditable;
import com.losagiles.CineAgile.otros.TipoAccion;
import com.losagiles.CineAgile.repository.ButacaRepository;
import com.losagiles.CineAgile.repository.SalaRepository;
import com.losagiles.CineAgile.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private ButacaRepository butacaRepository;

    @Auditable(value = TipoAccion.CREAR, nombreEntidad = "Sala", detalles = "Crear una nueva sala")
    @Transactional
    public ResCrearSala crearSala(SolicitudCrearSala solicitudCrearSala) {
        try {
            Sala sala = Sala.builder()
                    .codigoSala(solicitudCrearSala.codigoSala())
                    .categoria(solicitudCrearSala.categoria())
                    // .butacas(solicitudCrearSala.butacas())
                    .activo(true)
                    .sede(Sede.builder().id(solicitudCrearSala.idSede()).build())
                    .build();

            Sala salaGuardada = salaRepository.save(sala);

            if (solicitudCrearSala.butacas().isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResCrearSala.builder()
                        .error(ResSalaErrorCode.BUTACAS_NO_ENCONTRADAS)
                        .build();
            }
            for (int i = 0; i < solicitudCrearSala.butacas().size(); i++) {
                Butaca butaca = solicitudCrearSala.butacas().get(i);

                if (butaca.getFila() > 25) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResCrearSala.builder()
                            .error(ResSalaErrorCode.MAX_FILA_SOBREPASADA)
                            .build();
                }

                butaca.setSala(salaGuardada);
                butaca.setActivo(true);
            }

            butacaRepository.saveAll(solicitudCrearSala.butacas());
            return ResCrearSala.builder()
                    .sala(salaGuardada)
                    .build();
        } catch (DataIntegrityViolationException e) {
            return ResCrearSala.builder()
                    .error(ResSalaErrorCode.EXCEPCION_INTEGRACION_DATOS)
                    .build();
        }
    }

    @Auditable(value = TipoAccion.EDITAR, nombreEntidad = "Sala", detalles = "Editar sala")
    @Transactional
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

        if (solicitudEditarSala.butacas() == null)
            return ResEditarSalaResultCode.NO_ERROR;

        List<Long> butacasSolIds = solicitudEditarSala.butacas().stream().map(SolicitudButacaAccion::idButaca).toList();
        List<Butaca> allButacas = butacaRepository.findAllBySalaId(sala.getId());
        Map<Long, Butaca> butacaMap = allButacas.stream().filter(but -> butacasSolIds.contains(but.getId()))
                .collect(Collectors.toMap(Butaca::getId, but -> but));

        if (butacasSolIds.size() != butacaMap.size()) {
            return ResEditarSalaResultCode.BUTACAS_INVALIDAS;
        }

        for (SolicitudButacaAccion solicitud : solicitudEditarSala.butacas()) {
            Butaca but = butacaMap.get(solicitud.idButaca());

            if (solicitud.accion().equals("activar") || solicitud.accion().equals("desactivar")) {
                but.setActivo(solicitud.accion().equals("activar"));
            }
        }

        boolean todosDesactivados = true;
        for (Butaca but : allButacas) {
            if (but.isActivo()) {
                todosDesactivados = false;
                break;
            }
        }

        if (todosDesactivados) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // ROLLBACK
            return ResEditarSalaResultCode.ALL_BUTACAS_DESACTIVADAS;
        }

        butacaRepository.saveAll(butacaMap.values());
        return ResEditarSalaResultCode.NO_ERROR;
    }

    public boolean existsBySedeAndCodigoSala(Sede sede, String codigoSala) {
        return salaRepository.existsBySedeAndCodigoSala(sede, codigoSala);
    }

    @Auditable(value = TipoAccion.ALTERNAR_ESTADO, nombreEntidad = "Sala", detalles = "Alternar estado de sala")
    public Sala establecerEstadoSala(Long idSala, boolean activo) {
        Sala sala = salaRepository.findById(idSala).orElse(null);

        if (sala != null) {
            sala.setActivo(activo);
            salaRepository.save(sala);
        }

        return sala;
    }
}
