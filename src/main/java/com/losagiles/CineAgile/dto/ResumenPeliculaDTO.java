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
    private String sinopsis;
    private String director;

    public ResumenPeliculaDTO(PeliculaCarteleraDTO peliculaCarteleraDTO, FuncionDTO funcionDTO, String sinopsis, String director) {
        this.peliculaCarteleraDTO = peliculaCarteleraDTO;
    }
    
    
}
