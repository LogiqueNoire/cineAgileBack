package com.losagiles.CineAgile.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasaServiceImpl implements CasaService {
    @Autowired
    CasaRepository casaRepository;

    @Override
    public Casa mostrarCasa(Long id) {
        return casaRepository.getCasaById(id);
    }

    @Override
    public List<Casa> listarCasas() {
        return casaRepository.findAll();
    }

    @Override
    public Casa registrarCasa(Casa casa) {
        return casaRepository.save(casa);
    }
}
