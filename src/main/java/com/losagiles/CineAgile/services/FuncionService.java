/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.ButacaFuncionDTO;
import com.losagiles.CineAgile.dto.FuncionDTO;
import com.losagiles.CineAgile.dto.FuncionesPorSedeDTO;
import com.losagiles.CineAgile.entidades.Funcion;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.losagiles.CineAgile.entidades.Sala;
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

    public float precio(Funcion funcion, String persona) {
        Sala s = funcion.getSala();
        Personeable personeable;
        personeable = switch (persona.toLowerCase()) {
            case "general" -> new PersonaGeneral();
            case "mayores" -> new PersonaMayor();
            case "conadis" -> new PersonaConadis();
            case "niños" -> new PersonaNiño();
            default -> null;
        };
        switch(funcion.getDimension().toUpperCase()){
            case "2D" -> funcion.setDimensionable(new DimensionDosD());
            case "3D" -> funcion.setDimensionable(new DimensionTresD());
            default -> {}
        }
        switch(s.getCategoria().toUpperCase()){
            case "REGULAR" -> funcion.setCategorizable(new CategoriaRegular());
            case "PRIME" -> funcion.setCategorizable(new CategoriaPrime());
            default -> {}
        }

        float precio = personeable.precio(
    funcion.getPrecioBase()
            + funcion.getCategorizable().precio(funcion.getPrecioBase())
            + funcion.getDimensionable().precio()
        );

        return (float) (Math.round(precio * 10.0) / 10.0);
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
                    .filter(funcion -> funcion.getFechaHoraInicio().isAfter(fecha) && funcion.getFechaHoraInicio().getDayOfYear() == fecha.getDayOfYear())
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
    
    public Funcion getFuncionPorId(Long idFuncion){
        return funcionRepository.findById(idFuncion).orElse(null);
    }

    public List<ButacaFuncionDTO> mostrarButacasDeUnaFuncion(Long idFuncion) {
        return funcionRepository.getButacaCompuestoByFuncionId(idFuncion);
    }

    public List<FuncionDTO> buscarFuncionesPorSemanaConPelicula(LocalDateTime fecha, Long idPelicula, Long idSede){
        LocalDate fechaBase = fecha.toLocalDate();
        LocalDate inicioSemana = fechaBase.with(DayOfWeek.MONDAY);
        LocalDate finSemana = inicioSemana.plusDays(6);

        LocalDateTime inicioSemanaDateTime = inicioSemana.atStartOfDay();
        LocalDateTime finSemanaDateTime = finSemana.atTime(LocalTime.MAX);
        return funcionRepository.buscarFuncionesPorSemanaConPelicula(idPelicula, idSede, inicioSemanaDateTime, finSemanaDateTime);
    }

    public List<FuncionDTO> buscarFuncionesPorSemanaConSala(LocalDateTime fecha, Long idSala, Long idSede){
        LocalDate fechaBase = fecha.toLocalDate();
        LocalDate inicioSemana = fechaBase.with(DayOfWeek.MONDAY);
        LocalDate finSemana = inicioSemana.plusDays(6);

        LocalDateTime inicioSemanaDateTime = inicioSemana.atStartOfDay();
        LocalDateTime finSemanaDateTime = finSemana.atTime(LocalTime.MAX);
        return funcionRepository.buscarFuncionesPorSemanaConSala(idSala, idSede, inicioSemanaDateTime, finSemanaDateTime);
    }
}
