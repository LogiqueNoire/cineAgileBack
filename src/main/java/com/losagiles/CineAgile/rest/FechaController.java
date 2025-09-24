package com.losagiles.CineAgile.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

@RestController
public class FechaController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/api/v1/fecha-actual")
    public String obtenerFechaRealFormateada() {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            ResponseEntity<String> res = restTemplate.exchange(
                    "https://api.mapy.cz/v1/timezone/timezone?timezone=America/Lima&apikey=W0hanijcuVDXQM8EhM_1ARIwcViBTRuKz5eUIaIL2jk",
                    HttpMethod.GET,
                    entity,
                    String.class);

            String response = res.getBody();

            System.out.println("Respuesta del API: " + response);

            JSONObject json = new JSONObject(response);
            JSONObject timezone = json.getJSONObject("timezone");
            String datetime = timezone.getString("currentLocalTime");

            LocalDateTime zonedDateTime = LocalDateTime.parse(datetime);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            return zonedDateTime.format(formatter);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error al obtener la fecha: " + e.getMessage();
        }
    }
}
