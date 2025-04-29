/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.FuncionDTO;
import com.losagiles.CineAgile.entidades.Funcion;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.losagiles.CineAgile.entidades.Sede;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
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
}
