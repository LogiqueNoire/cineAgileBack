package com.losagiles.CineAgile.Entrada;

import com.losagiles.CineAgile.repository.EntradaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EntradaTest {
    @Autowired
    EntradaRepository entradaRepository;

    @Test //funcion con 5 entradas visto en bd
    public void prueba1(){
        boolean result = true;
        assertEquals(result, entradaRepository.tieneEntradas(Long.valueOf(109634)));

    }
}
