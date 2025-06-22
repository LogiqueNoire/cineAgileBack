package com.losagiles.CineAgile.Entrada;

import com.losagiles.CineAgile.repository.EntradaRepository;
import com.losagiles.CineAgile.seguridad.AESCipher;
import com.losagiles.CineAgile.services.EntradaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EntradaTest {
    @Autowired
    EntradaRepository entradaRepository;

    @Autowired
    EntradaService entradaService;

    @Test //funcion con 5 entradas visto en bd
    public void prueba1(){
        boolean result = true;
        assertEquals(result, entradaRepository.tieneEntradas(Long.valueOf(109634)));

    }

    @Test
    public void prueba2(){
        try{
            String[] result = AESCipher.decrypt("O7VJ3dhZYdCzrv+6OiQFpn1TpX76UeZdePXttLE504M=").split("_");
            System.out.println("prueba"+result[1]);
            System.out.println(entradaRepository.findById_IdFuncionAndId_IdButaca(Long.valueOf(result[0]), Long.valueOf(result[1])).get().getPersona());
        } catch (Exception e){
        }
        //System.out.println(entradaService.findEntrada("O7VJ3dhZYdCzrv+6OiQFpn1TpX76UeZdePXttLE504M="));

    }
}
