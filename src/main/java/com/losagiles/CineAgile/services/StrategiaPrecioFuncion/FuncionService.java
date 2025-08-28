/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services.StrategiaPrecioFuncion;

import com.losagiles.CineAgile.dto.ButacaFuncionDTO;
import com.losagiles.CineAgile.dto.FuncionDTO;
import com.losagiles.CineAgile.dto.FuncionesPorSedeDTO;
import com.losagiles.CineAgile.entidades.Funcion;

import java.time.*;

import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.otros.Auditable;
import com.losagiles.CineAgile.otros.TipoAccion;
import com.losagiles.CineAgile.repository.EntradaRepository;
import com.losagiles.CineAgile.repository.PeliculaRepository;
import com.losagiles.CineAgile.repository.SalaRepository;
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

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private EntradaRepository entradaRepository;

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
        LocalDateTime time = LocalDateTime.now(ZoneId.of("America/Lima"));
        time = time.minusMinutes(5);
        return funcionRepository.getButacaCompuestoByFuncionId(idFuncion, time);
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Función", detalles = "Consultar funciones de película en semana")
    public List<FuncionDTO> buscarFuncionesPorSemanaConPelicula(LocalDateTime fecha, Long idPelicula, Long idSede){
        LocalDate fechaBase = fecha.toLocalDate();
        LocalDate inicioSemana = fechaBase.with(DayOfWeek.MONDAY);
        LocalDate finSemana = inicioSemana.plusDays(6);

        LocalDateTime inicioSemanaDateTime = inicioSemana.atStartOfDay();
        LocalDateTime finSemanaDateTime = finSemana.atTime(LocalTime.MAX);
        return funcionRepository.buscarFuncionesPorSemanaConPelicula(idPelicula, idSede, inicioSemanaDateTime, finSemanaDateTime);
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Función", detalles = "Consultar funciones de sala en semana")
    public List<FuncionDTO> buscarFuncionesPorSemanaConSala(LocalDateTime fecha, Long idSala, Long idSede){
        LocalDate fechaBase = fecha.toLocalDate();
        LocalDate inicioSemana = fechaBase.with(DayOfWeek.MONDAY);
        LocalDate finSemana = inicioSemana.plusDays(6);

        LocalDateTime inicioSemanaDateTime = inicioSemana.atStartOfDay();
        LocalDateTime finSemanaDateTime = finSemana.atTime(LocalTime.MAX);
        return funcionRepository.buscarFuncionesPorSemanaConSala(idSala, idSede, inicioSemanaDateTime, finSemanaDateTime);
    }

    @Auditable(value = TipoAccion.EDITAR, nombreEntidad = "Función", detalles = "Editar datos de función")
    public Optional<Funcion> actualizarFuncion(FuncionDTO dto){
        Optional<Funcion> optional = funcionRepository.findById(dto.getIdFuncion());
        if (!optional.isPresent()) {
            return Optional.empty();
        }

        Funcion funcion = optional.get();
        Pelicula pelicula = peliculaRepository.findById(dto.getIdPelicula()).get();
        int duracion = pelicula.getDuracion();

        Sala sala = salaRepository.findById(dto.getIdSala()).get();
        funcion.setPelicula(pelicula);
        funcion.setSala(sala);
        funcion.setDimension(dto.getDimension());
        funcion.setPrecioBase(dto.getPrecioBase());
        if (dto.getFechaHoraInicio() != null){
            funcion.setFechaHoraInicio(dto.getFechaHoraInicio());
            funcion.setFechaHoraFin(dto.getFechaHoraInicio().plusMinutes(duracion));
        }
        System.out.println(funcion.getFechaHoraFin().toLocalTime());
        LocalDateTime inicioConMargen = funcion.getFechaHoraInicio().minusMinutes(20);
        LocalDateTime finConMargen = funcion.getFechaHoraFin().plusMinutes(20);
        if(!funcionRepository.cruceConIdFuncion(funcion.getSala().getId(), inicioConMargen, finConMargen, funcion.getId()) //no se haber cruce
                && !funcion.getFechaHoraInicio().toLocalTime().isBefore(LocalTime.of(8, 0))   // hora inicio >= 08:00
                && !funcion.getFechaHoraInicio().toLocalTime().isAfter(LocalTime.of(22, 30))   // hora inicio <= 20:30
                && !entradaRepository.tieneEntradas(funcion.getId()) ) //la funcion no debe tener entradas
        {
            funcionRepository.save(funcion);
            return Optional.of(funcion);
        } else
            return Optional.empty();
    }

    @Auditable(value = TipoAccion.CREAR, nombreEntidad = "Función", detalles = "Crear función")
    public Optional<Funcion> save(FuncionDTO dto){

        Funcion funcion = new Funcion();
        Pelicula pelicula = peliculaRepository.findById(dto.getIdPelicula()).get();
        int duracion = pelicula.getDuracion();
        Sala sala = salaRepository.findById(dto.getIdSala()).get();


        funcion.setPelicula(pelicula);
        funcion.setSala(sala);
        funcion.setDimension(dto.getDimension());
        funcion.setPrecioBase(dto.getPrecioBase());
        if (dto.getFechaHoraInicio() != null){
            funcion.setFechaHoraInicio(dto.getFechaHoraInicio());
            funcion.setFechaHoraFin(dto.getFechaHoraInicio().plusMinutes(duracion));
        }
        System.out.println(funcion.getFechaHoraInicio());

        System.out.println(funcion.getFechaHoraFin().toLocalTime());
        LocalDateTime inicioConMargen = funcion.getFechaHoraInicio().minusMinutes(20);
        LocalDateTime finConMargen = funcion.getFechaHoraFin().plusMinutes(20);
        if(!funcionRepository.cruce(funcion.getSala().getId(), inicioConMargen, finConMargen)
                && !funcion.getFechaHoraInicio().toLocalTime().isBefore(LocalTime.of(8, 0))   // hora inicio >= 08:00
                && !funcion.getFechaHoraInicio().toLocalTime().isAfter(LocalTime.of(22, 30)) )  // hora inicio <= 20:30
        {
            funcionRepository.save(funcion);
            return Optional.of(funcion);
        } else
            return Optional.empty();
    }

    public boolean estaDisponible(Long id) {
        Funcion funcion = funcionRepository.findById(id).orElse(null);

        if (funcion != null) {
            Sala sala = funcion.getSala();
            Sede sede = sala.getSede();

            return (sala.getActivo() == true && sede.getActivo() == true);
        }

        return false;
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Función", detalles = "Consultar cantidad de funciones agotadas")
    public Integer funcionesPorProyectar(LocalDateTime fecha){
        LocalDateTime inicio = fecha.toLocalDate().atStartOfDay();
        LocalDateTime fin = fecha.toLocalDate().plusDays(1).atStartOfDay();
        return funcionRepository.funcionesPorProyectarEnPeriodoTiempo(inicio, fin);
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Función", detalles = "Consultar cantidad de funciones agotadas")
    public Integer funcionesAgotadasEnPeriodoTiempo(LocalDateTime fecha){
        LocalDateTime inicio = fecha.toLocalDate().atStartOfDay();
        LocalDateTime fin = fecha.toLocalDate().plusDays(1).atStartOfDay();
        return funcionRepository.funcionesAgotadasEnPeriodoTiempo(inicio, fin);
    }
}
