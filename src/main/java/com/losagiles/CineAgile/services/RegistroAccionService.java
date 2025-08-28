package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.entidades.RegistroAccion;
import com.losagiles.CineAgile.otros.Auditable;
import com.losagiles.CineAgile.otros.TipoAccion;
import com.losagiles.CineAgile.repository.RegistroAccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RegistroAccionService {

    @Autowired
    private RegistroAccionRepository registroAccionRepository;

    public RegistroAccion registrarAccion(RegistroAccion accion) {
        return registroAccionRepository.save(accion);
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Auditoría", detalles = "Consultar registros de auditoría")
    public List<RegistroAccion> listarAcciones() {
        return registroAccionRepository.findAll();
    }
}


