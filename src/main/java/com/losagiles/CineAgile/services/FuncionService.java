/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.FuncionDTO;
import com.losagiles.CineAgile.entidades.Funcion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.losagiles.CineAgile.repository.FuncionRepository;

/**
 *
 * @author USUARIO
 */
@Service
public class FuncionService {
    @Autowired
    private FuncionRepository funcionRespository;
    
    public static float precio(Funcion funcion, Personeable personeable){
	return personeable.precio(
                funcion.getPrecioBase()
                +funcion.getCategorizable().precio(funcion.getPrecioBase())
                +funcion.getDimensionable().precio()
        );
    }

    public List<FuncionDTO> mostrarFuncionesDePelicula(Long idPelicula) {
        return funcionRespository.getFuncionesByPeliculaId(idPelicula);
    }

    public List<FuncionDTO> mostrarFuncionesDePeliculaDeFecha(Long idPelicula, LocalDate fecha) {
        List<FuncionDTO> funciones = funcionRespository.getFuncionesByPeliculaId(idPelicula);
        return funciones.stream().filter(funcion -> fecha.equals(funcion.getFechaHoraInicio().toLocalDate())).toList();
    }
}
