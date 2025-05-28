package com.losagiles.CineAgile.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

@RestController
public class FechaController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/fecha-actual")
    public String obtenerFechaRealFormateada() {
        try {
            String url = "https://timeapi.io/api/Time/current/zone?timeZone=America/Lima";
            String response = restTemplate.getForObject(url, String.class);
            System.out.println("Respuesta del API: " + response);

            JSONObject json = new JSONObject(response);
            String datetime = json.getString("dateTime");

            LocalDateTime zonedDateTime = LocalDateTime.parse(datetime);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            return zonedDateTime.format(formatter);
        } catch (Exception e) {
            return "Error al obtener la fecha: " + e.getMessage();
        }
    }
}
