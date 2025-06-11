package com.losagiles.CineAgile.sala;

import com.losagiles.CineAgile.dto.ResCrearSala;
import com.losagiles.CineAgile.dto.SolicitudCrearSala;
import com.losagiles.CineAgile.entidades.Butaca;
import com.losagiles.CineAgile.entidades.Sala;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.repository.SalaRepository;
import com.losagiles.CineAgile.repository.SedeRepository;
import com.losagiles.CineAgile.services.SalaButacasService;
import com.losagiles.CineAgile.services.SalaService;
import com.losagiles.CineAgile.services.SedeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class SalaTests {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private SalaService salaService;

    @Test
    public void crearMismoCodigoSalaEnDosSedesDiferente() {
        // Si no funciona, ajustar la secuencia de sala
        Sede sedeA = Sede.builder().nombre("__SedeA__").build();
        Sede sedeB = Sede.builder().nombre("__SedeB__").build();

        sedeA = sedeRepository.save(sedeA);
        sedeB = sedeRepository.save(sedeB);

        Sala salaA = Sala.builder()
                .sede(sedeA)
                .codigoSala("sopita")
                .categoria("Regular")
                .build();
        Sala salaB = Sala.builder()
                .sede(sedeB)
                .codigoSala("sopita")
                .categoria("Prime")
                .build();
        System.out.println(salaA.getId() + ", " + salaB.getId());

        salaA = salaRepository.save(salaA);
        salaB = salaRepository.save(salaB);

        System.out.println(sedeA.getId() + ", " + salaA.getId() + "(" + salaA.getCodigoSala() + ")");
        System.out.println(sedeB.getId() + ", " + salaB.getId() + "(" + salaB.getCodigoSala() + ")");

        // Limpiar
        salaRepository.deleteAll(List.of(salaA, salaB));
        sedeRepository.deleteAll(List.of(sedeA, sedeB));
    }

    @Test
    public void crearNuevaSalaConButacas() {
        Sede nuevaSede = Sede.builder().nombre("__sede_prueba__").build();
        nuevaSede = sedeRepository.save(nuevaSede);

        Butaca[] butaca = {
                Butaca.builder().fila(0).columna(0).discapacitado(false).build(),
                Butaca.builder().fila(1).columna(0).discapacitado(false).build(),
                Butaca.builder().fila(2).columna(0).discapacitado(false).build(),
                Butaca.builder().fila(3).columna(0).discapacitado(false).build(),
                Butaca.builder().fila(4).columna(0).discapacitado(false).build(),
        };

        SolicitudCrearSala solicitudCrearSala = SolicitudCrearSala.builder()
                .idSede(nuevaSede.getId())
                .codigoSala("sopita5")
                .categoria("Regular")
                .butacas(List.of(butaca))
                .build();

        ResCrearSala resultado = salaService.crearSala(solicitudCrearSala);
        System.out.println(nuevaSede.getId());

        if (resultado.sala() != null) {
            System.out.println("Sala creada!");
        }
    }
}
