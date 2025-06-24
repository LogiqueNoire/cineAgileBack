/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.losagiles.CineAgile.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.*;
import java.util.Objects;

/**
 *
 * @author JOSE
 */

@Data
@Embeddable
class EntradaId implements Serializable {
    private Long idFuncion;
    private Long idButaca;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntradaId)) return false;

        EntradaId otro = (EntradaId) o;
        return Objects.equals(idFuncion, otro.idFuncion) &&
                Objects.equals(idButaca, otro.idButaca);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFuncion, idButaca);
    }
}

@Data
@Entity
public class Entrada {
    @EmbeddedId
    private EntradaId id = new EntradaId();

    @Column
    private float costoFinal;

    @ManyToOne
    @JoinColumn (name = "id_funcion")
    @JsonBackReference
    @MapsId("idFuncion")
    private Funcion funcion;

    @OneToOne
    @MapsId("idButaca")
    private Butaca butaca;

    private LocalDateTime tiempoRegistro;
    private String estado; // "listo", "esperando"
    private String persona; // "general", "mayores", "conadis", "niños"

}
