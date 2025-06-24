package com.losagiles.CineAgile.rest;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/tipocambio")
public class TipoCambioController {
    @Value("${sunat.token}")
    private String sunatToken;

    @GetMapping
    public ResponseEntity<?> obtenerTipoCambio(@RequestParam String date) {
        String url = "https://api.apis.net.pe/v2/sunat/tipo-cambio?date=" + date;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + sunatToken);
            headers.set("Accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener tipo de cambio: " + e.getMessage());
        }
    }
}

