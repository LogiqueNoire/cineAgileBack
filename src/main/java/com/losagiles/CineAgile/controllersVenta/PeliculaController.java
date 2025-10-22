package com.losagiles.CineAgile.controllersVenta;

import com.losagiles.CineAgile.dto.PeliculaCarteleraDTO;
import com.losagiles.CineAgile.entidades.Pelicula;
import com.losagiles.CineAgile.services.PeliculaService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/venta/v1/peliculas")
public class PeliculaController {
    @Autowired
    PeliculaService peliculaService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping ("/{idPelicula}")
    private ResponseEntity<Pelicula> getPelicula(@PathVariable Long idPelicula) {

        return ResponseEntity.ofNullable(peliculaService.mostrarPelicula(idPelicula));
    }

    @GetMapping ("/encartelera")
    private ResponseEntity <List<PeliculaCarteleraDTO>> getEnCartelera(@RequestParam String fecha){
        LocalDateTime fechaParseada = LocalDateTime.parse(fecha.replace("Z", ""));
        LocalDateTime fechaAjustadaPeru = fechaParseada.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("America/Lima")).toLocalDateTime();
        System.out.println("Fecha ajustada: " + fechaAjustadaPeru);
        List<PeliculaCarteleraDTO> peliculas = peliculaService.mostrarPeliculasEnCartelera(fechaAjustadaPeru);

        // Mapear a PeliculaCarteleraDTO, el cual solo muestra información muy básica
        List<PeliculaCarteleraDTO> peliDTO = modelMapper.map(peliculas, new TypeToken<List<PeliculaCarteleraDTO>>() {}.getType());
        System.out.println(fecha);
        return ResponseEntity.ok(peliDTO);
    }

    @GetMapping ("/proximamente")
    private ResponseEntity <List<PeliculaCarteleraDTO>> getProximamente(@RequestParam String fecha){
        LocalDateTime fechaParseada = LocalDateTime.parse(fecha.replace("Z", ""));
        System.out.println("Fecha parseada: " + fechaParseada);
        List<PeliculaCarteleraDTO> peliculas = peliculaService.mostrarPeliculasProximamente(fechaParseada);
        return ResponseEntity.ok(peliculas);
    }
    /*
    @GetMapping ("/todas")
    private ResponseEntity <List<PeliculaCarteleraDTO>> getALl(){
        List<PeliculaCarteleraDTO> peliculas = peliculaService.getAll();
        return ResponseEntity.ok(peliculas);
    }
*/

}