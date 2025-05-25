/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.EntradaInfo;
import com.losagiles.CineAgile.dto.ReqRegistrarEntrada;
import com.losagiles.CineAgile.dto.ResRegistrarEntrada;
import com.losagiles.CineAgile.entidades.*;
import com.losagiles.CineAgile.repository.ButacaRepository;
import com.losagiles.CineAgile.repository.EntradaRepository;

import java.util.ArrayList;
import java.util.List;

import com.losagiles.CineAgile.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Devuelve null cuando la solicitud no es válida.
    public ResRegistrarEntrada registrarEntradas(ReqRegistrarEntrada solicitud) {
        Funcion funcion = funcionService.getFuncionPorId(solicitud.id_funcion());

        List<Long> butacaIds = solicitud.entradas().stream().map(EntradaInfo::id_butaca).toList();
        List<Butaca> butacas = butacaRepository.findAllById(butacaIds);

        // No existe alguna butaca
        if (butacaIds.size() != butacas.size()) {
            return null;
        }

        // Las butaca están bloqueadas o ya registradas
        List<Entrada> entradas = entradaRepository.findAllByButacaIdIn(butacaIds);
        for (Entrada entrada : entradas) {
            if (entrada.getEstado().equals("esperando") || entrada.getEstado().equals("listo")) {
                return null;
            }
        }

        List<Entrada> nuevasEntradas = new ArrayList<>();
        for (EntradaInfo info : solicitud.entradas()) {
            Entrada nueva = new Entrada();

            Butaca butaca = new Butaca();
            butaca.setId(info.id_butaca());
            nueva.setButaca(butaca);

            nueva.setFuncion(funcion);

            nueva.setEstado("listo");
            nueva.setPersona("normal");
            nueva.setTiempoRegistro(solicitud.tiempoRegistro());
            nueva.setCostoFinal(funcionService.precio(funcion, info.persona()));
            nuevasEntradas.add(nueva);
        }

        ResRegistrarEntrada res = new ResRegistrarEntrada(
                entradaRepository.saveAll(nuevasEntradas),
                funcion.getFechaHoraInicio(),
                funcion.getFechaHoraFin(),
                funcion.getSala().getCodigoSala(),
                funcion.getSala().getSede().getNombre(),
                funcion.getPelicula().getNombre()
        );

        return res;
    }

}
