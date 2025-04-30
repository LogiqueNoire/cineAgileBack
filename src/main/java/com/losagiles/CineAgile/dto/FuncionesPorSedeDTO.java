/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.dto;

import java.util.*;

/**
 *
 * @author USUARIO
 */
public class FuncionesPorSedeDTO {
    private Long idSede;
    private String nombreSede;
    private List<FuncionDTO> funciones = new ArrayList<>();

    public FuncionesPorSedeDTO(Long idSede, String nombreSede) {
        this.idSede = idSede;
        this.nombreSede = nombreSede;
    }

    public void agregarFuncion(FuncionDTO funcion) {
        funciones.add(funcion);
    }

    public List<FuncionDTO> getFunciones() {
        return funciones;
    }

    public Long getIdSede() {
        return idSede;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setFunciones(List<FuncionDTO> funciones) {
        this.funciones = funciones;
    }

    public void setIdSede(Long idSede) {
        this.idSede = idSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

}

