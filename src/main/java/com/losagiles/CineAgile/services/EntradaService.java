/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.EntradaInfo;
import com.losagiles.CineAgile.dto.ReqRegistrarEntrada;
import com.losagiles.CineAgile.entidades.Butaca;
import com.losagiles.CineAgile.entidades.Entrada;
import com.losagiles.CineAgile.entidades.Funcion;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.repository.ButacaRepository;
import com.losagiles.CineAgile.repository.EntradaRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.losagiles.CineAgile.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
/**
 *
 * @author JOSE
 */
@Service
public class EntradaService {
    @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    private FuncionService funcionService;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private ButacaRepository butacaRepository;

    public List<Entrada> listarEntradas() {
        return entradaRepository.findAll();
    }

    // Por ahora, devuelve una lista vacía cuando no es posible registrar una
    // entrada, sea por cualquier razón.
    public List<Entrada> registrarEntradas(ReqRegistrarEntrada solicitud) {
        Funcion funcion = funcionService.getFuncionPorId(solicitud.id_funcion());

        List<Long> butacaIds = solicitud.entradas().stream().map(EntradaInfo::id_butaca).toList();
        List<Butaca> butacas = butacaRepository.findAllById(butacaIds);

        // No existe alguna butaca
        if (butacaIds.size() != butacas.size()) {
            return new ArrayList<>();
        }

        // Las butaca están bloqueadas o ya registradas
        List<Entrada> entradas = entradaRepository.findAllByButacaIdIn(butacaIds);
        for (Entrada entrada : entradas) {
            if (entrada.getEstado().equals("esperando") || entrada.getEstado().equals("listo")) {
                return new ArrayList<>();
            }
        }

        List<Entrada> nuevasEntradas = new ArrayList<>();
        for (Butaca butaca : butacas) {
            Entrada nueva = new Entrada();

            nueva.setIdButaca(butaca.getId());
            nueva.setIdFuncion(funcion.getId());
            nueva.setButaca(butaca);
            nueva.setFuncion(funcion);

            // Calcular precio con la sala por aquí....

            nueva.setEstado("listo");
            nueva.setPersona("normal");
            nueva.setTiempoRegistro(solicitud.tiempoRegistro());
            nueva.setCostoFinal(10.0f);
            nuevasEntradas.add(nueva);
        }
        return entradaRepository.saveAll(nuevasEntradas);
    }

}
