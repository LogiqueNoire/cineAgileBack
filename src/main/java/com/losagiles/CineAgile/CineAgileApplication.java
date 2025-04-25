package com.losagiles.CineAgile;



import com.losagiles.CineAgile.services.CategoriaPrime;
import com.losagiles.CineAgile.services.DimensionTresD;
import com.losagiles.CineAgile.Funcion;
import com.losagiles.CineAgile.Pelicula;
import com.losagiles.CineAgile.services.PersonaConadis;
import com.losagiles.CineAgile.services.PersonaGeneral;
import com.losagiles.CineAgile.services.PersonaNiño;
import com.losagiles.CineAgile.Sala;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CineAgileApplication {

    public static void main(String[] args) {
        Sala sala1 = new Sala(1, 100, "Prime");
        
        Pelicula StarWars = new Pelicula(1,"Star Wars III - La Venganza de los Sith", 140 
                                        , "La tercera entrega de la trilogia" , "Sci-Fi" , "George Lucas" 
                                        , "+14" , "Hayden Christensen , Ewan McGregor , etc..." 
                                        , LocalDate.of(2025,04,23) , LocalDate.of(2025,04,27) , "Preventa");
        System.out.println(StarWars);
        
        
        Funcion f = new Funcion(0, LocalDateTime.of(2025, 04, 28, 20, 10), StarWars, "3D",
                20f, sala1, new CategoriaPrime(), new DimensionTresD());
        System.out.println("Holaa");
        System.out.println(f.precio(new PersonaGeneral()));
        System.out.println(f.precio(new PersonaNiño()));
        System.out.println(f.precio(new PersonaConadis()));
        
        System.out.println(f);
        
        SpringApplication.run(CineAgileApplication.class, args);
        /*
        Funcion f = new Funcion(0, new Date(2025,04,23), new Date(2025,04,23), "Prime",
                20f, new Sala(), new CategoriaPrime(), new DimensionTresD());
        System.out.println("Holaa");
        System.out.println(f.precio(new PersonaGeneral()));
        System.out.println(f.precio(new PersonaNiño()));
        System.out.println(f.precio(new PersonaConadis()));
        */
        
        /* Creo que es mejor usar LocalDate , debido a que bueno cuando ejecuté el print con el date para la peli
        salia esto: 
        , fechaInicioPreventa=Sat May 23 00:00:00 PET 3925
        , fechaInicioEstreno=Wed May 27 00:00:00 PET 3925 
        y creo que el "Date" maneja diferente las fechas en java porque por ejemplo dice que es May pero debe ser Apr
        y dice PET 3925 creo que dice año 3925 y eso no es asi ps xd
        */
        
    }


}