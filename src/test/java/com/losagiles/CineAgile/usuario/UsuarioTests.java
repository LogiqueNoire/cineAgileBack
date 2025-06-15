package com.losagiles.CineAgile.usuario;

import com.losagiles.CineAgile.dto.ResCrearUsuario;
import com.losagiles.CineAgile.dto.ResUsuarioErrorCode;
import com.losagiles.CineAgile.dto.SolicitudCrearUsuario;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.repository.SedeRepository;
import com.losagiles.CineAgile.services.UsuarioInternoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InvalidAttributeValueException;

@SpringBootTest
@Transactional
public class UsuarioTests {

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private UsuarioInternoService usuarioInternoService;

    @Test
    public void noSePuedecrearUsuarioConSedeInvalida() throws Exception {
        SolicitudCrearUsuario solicitudCrearUsuario = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("holacomoestas12345")
                .sedeId(51512521L)
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if ((res.error() != null && res.error() != ResUsuarioErrorCode.SEDE_NO_EXISTE)) {
            System.out.println(res.error().getDescripcion());
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void noSePuedecrearUsuarioConNombreCorto() throws Exception {
        Sede sedeA = Sede.builder().nombre("__SedeA__").build();
        sedeA = sedeRepository.save(sedeA);

        SolicitudCrearUsuario solicitudCrearUsuario = SolicitudCrearUsuario.builder()
                .username("c")
                .password("holacomoestas12345")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if ((res.error() != null && res.error() != ResUsuarioErrorCode.NOMBRE_USUARIO_INVALIDO)) {
            System.out.println(res.error().getDescripcion());
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void noSePuedecrearUsuarioCortoConEspacios() throws Exception {
        Sede sedeA = Sede.builder().nombre("__SedeA__").build();
        sedeA = sedeRepository.save(sedeA);

        SolicitudCrearUsuario solicitudCrearUsuario = SolicitudCrearUsuario.builder()
                .username("c               s")
                .password("holacomoestas12345")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if ((res.error() != null && res.error() != ResUsuarioErrorCode.NOMBRE_USUARIO_INVALIDO)) {
            System.out.println(res.error().getDescripcion());
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void noSePuedecrearUsuarioConCaracteresEspeciales() throws Exception {
        Sede sedeA = Sede.builder().nombre("__SedeA__").build();
        sedeA = sedeRepository.save(sedeA);

        SolicitudCrearUsuario solicitudCrearUsuario = SolicitudCrearUsuario.builder()
                .username("sc#_SDA22%1")
                .password("holaComoestas12345")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if ((res.error() != null && res.error() != ResUsuarioErrorCode.NOMBRE_USUARIO_INVALIDO)) {
            System.out.println(res.error().getDescripcion());
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void noSePuedecrearUsuarioYaExistente() throws Exception {
        Sede sedeA = Sede.builder().nombre("__SedeA__").build();
        sedeA = sedeRepository.save(sedeA);

        SolicitudCrearUsuario solicitudCrearUsuario = SolicitudCrearUsuario.builder()
                .username("spiderdog")
                .password("holaComoestas12345")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if ((res.error() != null && res.error() != ResUsuarioErrorCode.USUARIO_YA_EXISTE)) {
            System.out.println(res.error().getDescripcion());
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void noAceptaContrasenaMenorA8Digitos() throws Exception {
        Sede sedeA = Sede.builder().nombre("__SedeA__").build();
        sedeA = sedeRepository.save(sedeA);

        SolicitudCrearUsuario solicitudCrearUsuario = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("ho13A")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if ((res.error() != null && res.error() != ResUsuarioErrorCode.CONTRASENA_INVALIDA)) {
            System.out.println(res.error().getDescripcion());
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void noAceptaContrasenaSinMayusculas() throws Exception {
        Sede sedeA = Sede.builder().nombre("__SedeA__").build();
        sedeA = sedeRepository.save(sedeA);

        SolicitudCrearUsuario solicitudCrearUsuario = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("dska1212d12j12jd21")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if ((res.error() != null && res.error() != ResUsuarioErrorCode.CONTRASENA_INVALIDA)) {
            System.out.println(res.error().getDescripcion());
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void noAceptaContrasenaSinNumeros() throws Exception {
        Sede sedeA = Sede.builder().nombre("__SedeA__").build();
        sedeA = sedeRepository.save(sedeA);

        SolicitudCrearUsuario solicitudCrearUsuario = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("saasddjAJKJSdjASdkj")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if ((res.error() != null && res.error() != ResUsuarioErrorCode.CONTRASENA_INVALIDA)) {
            System.out.println(res.error().getDescripcion());
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void noAceptaContrasenaSinMinusculas() throws Exception {
        Sede sedeA = Sede.builder().nombre("__SedeA__").build();
        sedeA = sedeRepository.save(sedeA);

        SolicitudCrearUsuario solicitudCrearUsuario = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("SF12NJ12F1JF1")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if ((res.error() != null && res.error() != ResUsuarioErrorCode.CONTRASENA_INVALIDA)) {
            System.out.println(res.error().getDescripcion());
            throw new IllegalArgumentException();
        }
    }


    @Test
    public void aceptaContrasenaCorrecta() throws Exception {
        Sede sedeA = Sede.builder().nombre("__SedeA__").build();
        sedeA = sedeRepository.save(sedeA);

        SolicitudCrearUsuario solicitudCrearUsuario = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("ojAsdjasj5adj")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if ((res.error() != null && res.error() != ResUsuarioErrorCode.CONTRASENA_INVALIDA)) {
            System.out.println(res.error().getDescripcion());
            throw new IllegalArgumentException();
        }
    }
}
