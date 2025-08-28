/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.entidades.*;
import com.losagiles.CineAgile.otros.Auditable;
import com.losagiles.CineAgile.otros.TipoAccion;
import com.losagiles.CineAgile.repository.ButacaRepository;
import com.losagiles.CineAgile.repository.EntradaRepository;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import com.losagiles.CineAgile.repository.SalaRepository;
import com.losagiles.CineAgile.seguridad.AESCipher;
import com.losagiles.CineAgile.services.StrategiaPrecioFuncion.FuncionService;
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
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Lima"));
        now = now.minusMinutes(5);

        List<Entrada> entradas = entradaRepository.findAllByFuncionIdAndButacaIdIn(funcion.getId(), butacaIds);

        for (Entrada entrada : entradas) {
            if ((entrada.getEstado().equals("esperando") && entrada.getTiempoRegistro().isBefore(now)) ||
                    entrada.getEstado().equals("listo")) {
                return ResRegistrarEntrada.error(ResRegEntradaStatusCode.BUTACAS_OCUPADAS);
            }
        }

        Iterator<Entrada> iteratorEntrada = entradas.iterator();

        List<Entrada> nuevasEntradas = new ArrayList<>();
        for (EntradaInfo info : solicitud.entradas()) {
            Entrada ent = entradas.isEmpty() ? new Entrada() : iteratorEntrada.next();

            if (entradas.isEmpty()) {
                ent.setButaca(Butaca.builder().id(info.id_butaca()).build());
                ent.setFuncion(funcion);
            }

            ent.setEstado("listo");
            ent.setPersona(info.persona());
            ent.setTiempoRegistro(solicitud.tiempoRegistro());
            ent.setCostoFinal(funcionService.precio(funcion, info.persona()));
            nuevasEntradas.add(ent);
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
                funcion.getPelicula().getClasificacion(),
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
            String clasificacion = e.getFuncion().getPelicula().getClasificacion();
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
                    clasificacion,
                    tokens
            );
            return entradasCompradasDTO;
        } catch (Exception e){
            return null;
        }
    }

    public ResRegistrarEntrada lockEntradas(ReqRegistrarEntrada reqRegistrarEntrada) {
        // Limitar el nro. de entradas a 5
        if (reqRegistrarEntrada.entradas().size() > 5)
            return ResRegistrarEntrada.error(ResRegEntradaStatusCode.NRO_ENTRADAS_INVALIDAS);

        Funcion funcion = funcionService.getFuncionPorId(reqRegistrarEntrada.id_funcion());

        if (funcion == null)
            return ResRegistrarEntrada.error(ResRegEntradaStatusCode.FUNCION_INVALIDA);

        if (funcion.getFechaHoraInicio().isBefore(reqRegistrarEntrada.tiempoRegistro()))
            return ResRegistrarEntrada.error(ResRegEntradaStatusCode.FECHA_INCORRECTA);

        List<Long> butacaIds = reqRegistrarEntrada.entradas().stream().map(EntradaInfo::id_butaca).toList();
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
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Lima"));
        now = now.minusMinutes(5);

        List<Entrada> entradas = entradaRepository.findAllByFuncionIdAndButacaIdIn(funcion.getId(), butacaIds);

        if (!entradas.isEmpty()) {
            for (Entrada entrada : entradas) {
                if (entrada.getEstado().equals("listo")) {
                    return ResRegistrarEntrada.error(ResRegEntradaStatusCode.BUTACAS_OCUPADAS);
                }
                entrada.setTiempoRegistro(reqRegistrarEntrada.tiempoRegistro());
            }

            entradaRepository.saveAll(entradas);
        }
        else {
            List<Entrada> entradasReservadas = new ArrayList<>();
            for (EntradaInfo entradaInfo : reqRegistrarEntrada.entradas()) {
                Entrada nuevaEntrada = new Entrada();
                nuevaEntrada.setFuncion(funcion);
                nuevaEntrada.setButaca(Butaca.builder().id(entradaInfo.id_butaca()).build());
                nuevaEntrada.setTiempoRegistro(reqRegistrarEntrada.tiempoRegistro());
                nuevaEntrada.setEstado("esperando");
                entradasReservadas.add(nuevaEntrada);
            }

            entradaRepository.saveAll(entradasReservadas);
        }

        return new ResRegistrarEntrada(null, ResRegEntradaStatusCode.OK_RESERVA);
    }

    public ResRegistrarEntrada unlock(ReqRegistrarEntrada reqRegistrarEntrada) {
        // Limitar el nro. de entradas a 5
        if (reqRegistrarEntrada.entradas().size() > 5)
            return ResRegistrarEntrada.error(ResRegEntradaStatusCode.NRO_ENTRADAS_INVALIDAS);

        Funcion funcion = funcionService.getFuncionPorId(reqRegistrarEntrada.id_funcion());

        if (funcion == null)
            return ResRegistrarEntrada.error(ResRegEntradaStatusCode.FUNCION_INVALIDA);

        if (funcion.getFechaHoraInicio().isBefore(reqRegistrarEntrada.tiempoRegistro()))
            return ResRegistrarEntrada.error(ResRegEntradaStatusCode.FECHA_INCORRECTA);

        List<Long> butacaIds = reqRegistrarEntrada.entradas().stream().map(EntradaInfo::id_butaca).toList();
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

        // Las butaca ya registradas
        List<Entrada> entradas = entradaRepository.findAllByFuncionIdAndButacaIdIn(funcion.getId(), butacaIds);
        for (Entrada entrada : entradas) {
            if (entrada.getEstado().equals("listo")) {
                return ResRegistrarEntrada.error(ResRegEntradaStatusCode.BUTACAS_OCUPADAS);
            }
        }

        entradaRepository.deleteAllInBatch(entradas);
        return new ResRegistrarEntrada(null, ResRegEntradaStatusCode.OK_LIBERAR);
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Entradas", detalles = "Consultar entradas vendidas en periodo")
    public int entradasVendidasEnPeriodoTiempo(LocalDateTime fecha){
        LocalDateTime inicio = fecha.toLocalDate().atStartOfDay();
        LocalDateTime fin = fecha.toLocalDate().plusDays(1).atStartOfDay();
        return entradaRepository.entradasVendidasEnPeriodoTiempo(inicio, fin);
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Entradas", detalles = "Consultar ventas en periodo")
    public BigDecimal ventasEnPeriodoTiempo(LocalDateTime fecha) {
        LocalDateTime inicio = fecha.toLocalDate().atStartOfDay();
        LocalDateTime fin = fecha.toLocalDate().plusDays(1).atStartOfDay();
        return entradaRepository.ventasEnPeriodoTiempo(inicio, fin);
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Varias", detalles = "Consultar desempeño semanal (ventas) de pelicula en periodo")
    public LinkedList<DiaHoraVentaDTO> getDesempenoSemanal(Long idPelicula, LocalDateTime fecha) {
        LocalDateTime inicioSemana = fecha.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
        LocalDateTime finSemana = inicioSemana.plusDays(6).toLocalDate().atTime(LocalTime.MAX);
        List<Object[]> listaObjetos = entradaRepository.ventasDetalladaPelicula(inicioSemana, finSemana, idPelicula);
        List<DiaHoraVentaDTO> lista = listaObjetos.stream()
                .map(obj -> new DiaHoraVentaDTO(
                        ((Number) obj[0]).intValue(),
                        ((Number) obj[1]).intValue(),
                        new BigDecimal(obj[2].toString())
                ))
                .collect(Collectors.toList());
        int dia = inicioSemana.getDayOfMonth();
        LinkedList<DiaHoraVentaDTO> resultado = new LinkedList<>();
        LinkedList<Integer> dias = new LinkedList<>();
        for(int i = 0; i < 7; i++){
            dias.add(inicioSemana.plusDays(i).getDayOfMonth());
        }
        for(Integer d: dias){
            for(int h=0; h<=24; h++){
                if(!lista.isEmpty() && lista.getFirst().getDia() == d && lista.getFirst().getHora() == h)
                    resultado.add(lista.removeFirst());
                else
                    resultado.add(new DiaHoraVentaDTO(d, h, BigDecimal.valueOf(0)));
            }
        }
        return resultado;
    }
}
