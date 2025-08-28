package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.RespuestaLogin;
import com.losagiles.CineAgile.dto.SolicitudLogin;
import com.losagiles.CineAgile.entidades.RegistroAccion;
import com.losagiles.CineAgile.entidades.Usuario;
import com.losagiles.CineAgile.otros.Auditable;
import com.losagiles.CineAgile.otros.TipoAccion;
import com.losagiles.CineAgile.repository.UsuarioRepository;
import com.losagiles.CineAgile.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RegistroAccionService registroAccionService;

    public RespuestaLogin login(SolicitudLogin solicitudLogin) {
        Usuario usuario = usuarioRepository.findByUsername(solicitudLogin.username()).orElse(null);

        if (usuario != null) {
            String password = usuario.getPassword();
            RegistroAccion registroAccion = new RegistroAccion();

            if (passwordEncoder.matches(solicitudLogin.password(), password)) {
                registroAccion.setAccion(TipoAccion.LOGIN.name());
                registroAccion.setUsuario(usuario.getUsername());
                registroAccion.setEntidadAfectada("");
                registroAccion.setDetalles("Logueo exitoso para el usuario "+usuario.getUsername());
                registroAccionService.registrarAccion(registroAccion);

                String token = jwtUtil.generarToken(usuario.getUsername());
                return new RespuestaLogin(token, null);
            }
            registroAccion.setAccion(TipoAccion.LOGIN.name());
            registroAccion.setUsuario(usuario.getUsername());
            registroAccion.setEntidadAfectada("");
            registroAccion.setDetalles("Contraseña invalida para el usuario "+usuario.getUsername());
            registroAccionService.registrarAccion(registroAccion);
            return new RespuestaLogin(null, "Contraseña invalida");
        }

        return new RespuestaLogin(null, "El usuario no existe");
    }

    public void establecerContra(String nuevaContra) {
        Usuario usuario = usuarioRepository.findByUsername("spiderdog").orElse(null);
        if (usuario != null) {
            usuario.setPassword(passwordEncoder.encode(nuevaContra));
            usuarioRepository.save(usuario);
        }
    }
}
