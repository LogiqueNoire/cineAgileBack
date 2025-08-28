package com.losagiles.CineAgile.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "auditoria")
@Data
public class RegistroAccion {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String usuario;
        private String accion;
        private String entidadAfectada;
        private Long idEntidad; //el id de la entidad afectada
        private String ip;

        private String detalles;

        @Column(nullable = false, updatable = false)
        private LocalDateTime fecha = ZonedDateTime.now(ZoneId.of("America/Lima")).toLocalDateTime();

}
