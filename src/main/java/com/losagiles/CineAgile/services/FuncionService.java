/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.ButacaFuncionDTO;
import com.losagiles.CineAgile.dto.FuncionDTO;
import com.losagiles.CineAgile.dto.FuncionesPorSedeDTO;
import com.losagiles.CineAgile.entidades.Funcion;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.losagiles.CineAgile.repository.FuncionRepository;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

/**
 *
 * @author USUARIO
 */
@Service
public class FuncionService {

    @Autowired
    private FuncionRepository funcionRepository;

    public static float precio(Funcion funcion, Personeable personeable) {
        return personeable.precio(
                funcion.getPrecioBase()
                + funcion.getCategorizable().precio(funcion.getPrecioBase())
                + funcion.getDimensionable().precio()
        );
    }

    public List<FuncionDTO> mostrarFuncionesDePelicula(Long idPelicula) {
        return funcionRepository.getFuncionesByPeliculaId(idPelicula);
    }

    public List<FuncionDTO> mostrarFuncionesDePeliculaDeFecha(Long idPelicula, LocalDate fecha) {
        List<FuncionDTO> funciones = funcionRepository.getFuncionesByPeliculaId(idPelicula);
        return funciones.stream().filter(funcion -> fecha.equals(funcion.getFechaHoraInicio().toLocalDate())).toList();
    }

    public List<FuncionesPorSedeDTO> mostrarFuncionesAgrupadasPorSede(Long idPelicula, LocalDateTime fecha) {
        List<FuncionDTO> funciones = funcionRepository.getFuncionesByPeliculaId(idPelicula);

        System.out.println("Solicitud recibida para la película con ID: " + idPelicula);
        System.out.println("Solicitud recibida para la fecha: " + fecha);

        List<FuncionDTO> funcionesFiltradas = funciones.stream()
                .filter(funcion -> funcion.getFechaHoraInicio().isAfter(fecha))
                .collect(Collectors.toList());

        Map<Long, FuncionesPorSedeDTO> mapa = new LinkedHashMap<>();

        for (FuncionDTO funcion : funcionesFiltradas) {
            Long idSede = (long) funcion.getIdSede();
            String nombreSede = funcion.getNombreSede();

            mapa.putIfAbsent(idSede, new FuncionesPorSedeDTO(idSede, nombreSede));
            mapa.get(idSede).agregarFuncion(funcion);
        }

        return new ArrayList<>(mapa.values());
    }

    public List<ButacaFuncionDTO> mostrarButacasDeUnaFuncion(Long idFuncion) {
        return funcionRepository.getButacaCompuestoByFuncionId(idFuncion);
    }
    
    
}
