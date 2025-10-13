package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.entidades.RegistroAccion;
import com.losagiles.CineAgile.otros.Auditable;
import com.losagiles.CineAgile.otros.TipoAccion;
import com.losagiles.CineAgile.repository.RegistroAccionRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RegistroAccionService {

    @Autowired
    private RegistroAccionRepository registroAccionRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RegistroAccion registrarAccion(RegistroAccion accion) {
        return registroAccionRepository.saveAndFlush(accion);
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Auditoría", detalles = "Consultar registros de auditoría")
    @Transactional
    public List<RegistroAccion> listarAcciones() {
        entityManager.clear();
        return registroAccionRepository.findAll();
    }
}


