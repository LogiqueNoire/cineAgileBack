package com.losagiles.CineAgile.test;

import com.losagiles.CineAgile.CategoriaPrime;
import com.losagiles.CineAgile.DimensionTresD;
import com.losagiles.CineAgile.Funcion;
import com.losagiles.CineAgile.Pelicula;
import com.losagiles.CineAgile.PersonaConadis;
import com.losagiles.CineAgile.PersonaGeneral;
import com.losagiles.CineAgile.PersonaNiño;
import com.losagiles.CineAgile.Sala;
import java.util.Date;
import java.time.LocalDate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CineAgileApplication {

    public static void main(String[] args) {
        Funcion f = new Funcion(0, new Date(2025,04,23), new Date(2025,04,23), "Prime",
                20f, new Sala(), new CategoriaPrime(), new DimensionTresD());
        System.out.println("Holaa");
        System.out.println(f.precio(new PersonaGeneral()));
        System.out.println(f.precio(new PersonaNiño()));
        System.out.println(f.precio(new PersonaConadis()));
        
        /* Creo que es mejor usar LocalDate , debido a que bueno cuando ejecuté el print con el date para la peli
        salia esto: 
        , fechaInicioPreventa=Sat May 23 00:00:00 PET 3925
        , fechaInicioEstreno=Wed May 27 00:00:00 PET 3925 
        y creo que el "Date" maneja diferente las fechas en java porque por ejemplo dice que es May pero debe ser Apr
        y dice PET 3925 creo que dice año 3925 y eso no es asi ps xd
        */
        Pelicula StarWars = new Pelicula(1,"Star Wars III - La Venganza de los Sith", 140 
                                        , "La tercera entrega de la trilogia" , "Sci-Fi" , "George Lucas" 
                                        , "+14" , "Hayden Christensen , Ewan McGregor , etc..." 
                                        , LocalDate.of(2025,04,23) , LocalDate.of(2025,04,27) , "Preventa");
        System.out.println(StarWars);
    }


}
