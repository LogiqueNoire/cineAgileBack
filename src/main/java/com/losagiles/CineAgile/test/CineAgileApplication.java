package com.losagiles.CineAgile.test;

import com.losagiles.CineAgile.CategoriaPrime;
import com.losagiles.CineAgile.DimensionTresD;
import com.losagiles.CineAgile.Funcion;
import com.losagiles.CineAgile.PersonaConadis;
import com.losagiles.CineAgile.PersonaGeneral;
import com.losagiles.CineAgile.PersonaNiño;
import com.losagiles.CineAgile.Sala;
import java.util.Date;
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
        
    }


}
