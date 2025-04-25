package com.losagiles.CineAgile.test;

import java.util.List;

public interface CasaService {
    public Casa mostrarCasa(Long id);
    public List<Casa> listarCasas();
    public Casa registrarCasa(Casa casa);
}
