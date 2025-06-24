/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.entidades.*;
import com.losagiles.CineAgile.repository.ButacaRepository;
import com.losagiles.CineAgile.repository.EntradaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.losagiles.CineAgile.repository.SalaRepository;
import com.losagiles.CineAgile.seguridad.AESCipher;
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
        // Limitar el nro. de entradas a 5
        if (solicitud.entradas().size() > 5)
            return ResRegistrarEntrada.error(ResRegEntradaStatusCode.NRO_ENTRADAS_INVALIDAS);

        Funcion funcion = funcionService.getFuncionPorId(solicitud.id_funcion());

        if (funcion == null)
            return ResRegistrarEntrada.error(ResRegEntradaStatusCode.FUNCION_INVALIDA);

        if (funcion.getFechaHoraInicio().isBefore(solicitud.tiempoRegistro()))
            return ResRegistrarEntrada.error(ResRegEntradaStatusCode.FECHA_INCORRECTA);

        List<Long> butacaIds = solicitud.entradas().stream().map(EntradaInfo::id_butaca).toList();
        List<Butaca> butacas = funcion.getSala().getButacas().stream()
                .filter(butaca -> butacaIds.contains(butaca.getId()))
                .toList();

        // No existe alguna butaca
        if (butacaIds.size() != butacas.size())
            return ResRegistrarEntrada.error(ResRegEntradaStatusCode.BUTACAS_INCORRECTAS);

        for (Butaca but : butacas) {
            if (but.getSala().getId().compareTo(funcion.getSala().getId()) != 0)
                return ResRegistrarEntrada.error(ResRegEntradaStatusCode.BUTACAS_INCORRECTAS);
        }

        // Las butaca están bloqueadas o ya registradas
        List<Entrada> entradas = entradaRepository.findAllByFuncionIdAndButacaIdIn(funcion.getId(), butacaIds);
        for (Entrada entrada : entradas) {
            if (entrada.getEstado().equals("esperando") || entrada.getEstado().equals("listo")) {
                return ResRegistrarEntrada.error(ResRegEntradaStatusCode.BUTACAS_OCUPADAS);
            }
        }

        List<Entrada> nuevasEntradas = new ArrayList<>();
        for (EntradaInfo info : solicitud.entradas()) {
            Entrada nueva = new Entrada();

            nueva.setButaca(Butaca.builder().id(info.id_butaca()).build());
            nueva.setFuncion(funcion);
            nueva.setEstado("listo");
            nueva.setPersona(info.persona());
            nueva.setTiempoRegistro(solicitud.tiempoRegistro());
            nueva.setCostoFinal(funcionService.precio(funcion, info.persona()));
            nuevasEntradas.add(nueva);
        }

        List <Entrada> listaEntradas = entradaRepository.saveAll(nuevasEntradas);
        List <String> tokens = new LinkedList<>();
        for(Entrada e: listaEntradas)
            try {
                tokens.add(AESCipher.encrypt(String.valueOf(e.getFuncion().getId()) + "_" + String.valueOf(e.getButaca().getId())));
            } catch (Exception ex) {
                tokens.add("");
            }


        EntradasCompradasDTO entradasCompradas = new EntradasCompradasDTO(
                listaEntradas,
                funcion.getFechaHoraInicio(),
                funcion.getFechaHoraFin(),
                funcion.getSala().getCodigoSala(),
                funcion.getSala().getSede().getNombre(),
                funcion.getPelicula().getNombre(),
                tokens
        );

        return ResRegistrarEntrada.ok(entradasCompradas);
    }


    public EntradasCompradasDTO findEntrada(String token) {
        try{
            String[] result = AESCipher.decrypt(token).split("_");
            System.out.println("prueba"+result);

            Entrada e = entradaRepository.findById_IdFuncionAndId_IdButaca(Long.valueOf(result[0]), Long.valueOf(result[1])).get();
            LocalDateTime fechaHoraInicio = e.getFuncion().getFechaHoraInicio();
            LocalDateTime fechaHoraFin = e.getFuncion().getFechaHoraFin();
            String sala = e.getFuncion().getSala().getCodigoSala();
            String nombreSede = e.getFuncion().getSala().getSede().getNombre();
            String tituloPelicula = e.getFuncion().getPelicula().getNombre();
            LinkedList<Entrada> listaEntradas = new LinkedList<>();
            listaEntradas.add(e);
            LinkedList<String> tokens = new LinkedList<>();
            tokens.add(token);
            EntradasCompradasDTO entradasCompradasDTO = new EntradasCompradasDTO(
                    listaEntradas,
                    fechaHoraInicio,
                    fechaHoraFin,
                    sala,
                    nombreSede,
                    tituloPelicula,
                    tokens
            );
            return entradasCompradasDTO;
        } catch (Exception e){
            return null;
        }
    }
}
