package com.losagiles.CineAgile.usuario;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.losagiles.CineAgile.dto.responses.ResCrearUsuario;
import com.losagiles.CineAgile.dto.responses.ResUsuarioErrorCode;
import com.losagiles.CineAgile.dto.solicitudes.SolicitudCrearUsuario;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.entidades.Usuario;
import com.losagiles.CineAgile.repository.SedeRepository;
import com.losagiles.CineAgile.repository.UsuarioRepository;
import com.losagiles.CineAgile.services.UsuarioInternoService;

@ExtendWith(MockitoExtension.class)
public class UsuarioTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SedeRepository sedeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioInternoService usuarioInternoService;

    private Sede sedeA;

    @BeforeEach
    void setUp() {
        sedeA = Sede.builder().id(1L).nombre("__SedeA__").activo(true).build();
    }

    @Test
    void crearUsuario_conSedeInvalida_debeDevolverError() {
        SolicitudCrearUsuario solicitud = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("holacomoestas12345")
                .sedeId(51512521L)
                .build();

        when(usuarioRepository.findByUsername(eq(solicitud.username()))).thenReturn(Optional.empty());
        when(sedeRepository.findById(eq(51512521L))).thenReturn(Optional.empty());

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitud);

        assertNotNull(res.error());
        assertEquals(ResUsuarioErrorCode.SEDE_NO_EXISTE, res.error());
    }

    @Test
    void crearUsuario_conNombreCorto_debeDevolverError() {
        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(sedeRepository.findById(eq(sedeA.getId()))).thenReturn(Optional.of(sedeA));

        SolicitudCrearUsuario solicitud = SolicitudCrearUsuario.builder()
                .username("c")
                .password("holacomoestas12345")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitud);

        assertNotNull(res.error());
        assertEquals(ResUsuarioErrorCode.NOMBRE_USUARIO_INVALIDO, res.error());
    }

    @Test
    void crearUsuario_conNombreConEspacios_debeDevolverError() {
        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(sedeRepository.findById(eq(sedeA.getId()))).thenReturn(Optional.of(sedeA));

        SolicitudCrearUsuario solicitud = SolicitudCrearUsuario.builder()
                .username("c               s")
                .password("holacomoestas12345")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitud);

        assertNotNull(res.error());
        assertEquals(ResUsuarioErrorCode.NOMBRE_USUARIO_INVALIDO, res.error());
    }

    @Test
    void crearUsuario_usuarioYaExistente_debeDevolverError() {
        when(usuarioRepository.findByUsername(eq("spiderdog")))
                .thenReturn(Optional.of(Usuario.builder().id(2L).username("spiderdog").build()));

        SolicitudCrearUsuario solicitud = SolicitudCrearUsuario.builder()
                .username("spiderdog")
                .password("holaComoestas12345")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitud);

        assertNotNull(res.error());
        assertEquals(ResUsuarioErrorCode.USUARIO_YA_EXISTE, res.error());
    }

    @Test
    void crearUsuario_contrasenaMenorDe8_debeDevolverError() {
        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(sedeRepository.findById(eq(sedeA.getId()))).thenReturn(Optional.of(sedeA));

        SolicitudCrearUsuario solicitud = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("ho13A")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitud);

        assertNotNull(res.error());
        assertEquals(ResUsuarioErrorCode.CONTRASENA_INVALIDA, res.error());
    }

    @Test
    void crearUsuario_contrasenaSinMayusculas_debeDevolverError() {
        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(sedeRepository.findById(eq(sedeA.getId()))).thenReturn(Optional.of(sedeA));

        SolicitudCrearUsuario solicitud = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("dska1212d12j12jd21")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitud);

        assertNotNull(res.error());
        assertEquals(ResUsuarioErrorCode.CONTRASENA_INVALIDA, res.error());
    }

    @Test
    void crearUsuario_contrasenaSinNumeros_debeDevolverError() {
        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(sedeRepository.findById(eq(sedeA.getId()))).thenReturn(Optional.of(sedeA));

        SolicitudCrearUsuario solicitud = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("saasddjAJKJSdjASdkj")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitud);

        assertNotNull(res.error());
        assertEquals(ResUsuarioErrorCode.CONTRASENA_INVALIDA, res.error());
    }

    @Test
    void crearUsuario_contrasenaSinMinusculas_debeDevolverError() {
        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(sedeRepository.findById(eq(sedeA.getId()))).thenReturn(Optional.of(sedeA));

        SolicitudCrearUsuario solicitud = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("SF12NJ12F1JF1")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitud);

        assertNotNull(res.error());
        assertEquals(ResUsuarioErrorCode.CONTRASENA_INVALIDA, res.error());
    }

    @Test
    void crearUsuario_contrasenaValida_debeCrearUsuario() {
        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(sedeRepository.findById(eq(sedeA.getId()))).thenReturn(Optional.of(sedeA));
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(usuarioRepository.save(any())).thenAnswer(invocation -> {
            Usuario u = invocation.getArgument(0);
            u.setId(99L);
            return u;
        });

        SolicitudCrearUsuario solicitud = SolicitudCrearUsuario.builder()
                .username("spiderdoggyeronny")
                .password("ojAsdjasj5adj")
                .sedeId(sedeA.getId())
                .build();

        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitud);

        assertNull(res.error());
        assertNotNull(res.usuario());
        assertEquals("spiderdoggyeronny", res.usuario().getUsername());
    }

}
