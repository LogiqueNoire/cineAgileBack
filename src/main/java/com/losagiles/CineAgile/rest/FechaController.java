package com.losagiles.CineAgile.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

@RestController
public class FechaController {

    private final WebClient webClient = WebClient.create("http://worldtimeapi.org");

    @GetMapping("/fecha-actual")
    public Mono<String> obtenerFechaRealFormateada() {
        return webClient
                .get()
                .uri("/api/timezone/America/Lima")
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    try {
                        // Extraer el valor de "datetime"
                        String datetime = json.split("\"datetime\":\"")[1].split("\"")[0];

                        // Parsear a objeto ZonedDateTime
                        ZonedDateTime zonedDateTime = ZonedDateTime.parse(datetime);

                        // Formatear a string limpio
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        return zonedDateTime.format(formatter);
                    } catch (Exception e) {
                        return "Error al procesar la fecha: " + e.getMessage();
                    }
                });
    }
}
