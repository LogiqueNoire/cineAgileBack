/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.dto;

/**
 *
 * @author USUARIO
 */
public class ResumenPeliculaDTO {
    private PeliculaCarteleraDTO peliculaCarteleraDTO;
    private FuncionDTO funcionDTO;
    
    public ResumenPeliculaDTO(PeliculaCarteleraDTO peliculaCarteleraDTO, FuncionDTO funcionDTO) {
        this.peliculaCarteleraDTO = peliculaCarteleraDTO;
        this.funcionDTO = funcionDTO;
    }
}
