package com.losagiles.CineAgile.services;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.entidades.Sede;
import com.losagiles.CineAgile.entidades.Usuario;
import com.losagiles.CineAgile.otros.Auditable;
import com.losagiles.CineAgile.otros.TipoAccion;
import com.losagiles.CineAgile.repository.SedeRepository;
import com.losagiles.CineAgile.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioInternoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Auditable(value = TipoAccion.CREAR, nombreEntidad = "Usuario", detalles = "Crear usuario")
    public ResCrearUsuario crearUsuario(SolicitudCrearUsuario solicitudCrearUsuario) {
        Usuario existente = usuarioRepository.findByUsername(solicitudCrearUsuario.username()).orElse(null);
        if (existente != null)
            return ResCrearUsuario.Error(ResUsuarioErrorCode.USUARIO_YA_EXISTE);

        Sede sede = sedeRepository.findById(solicitudCrearUsuario.sedeId()).orElse(null);
        if (sede == null)
            return ResCrearUsuario.Error(ResUsuarioErrorCode.SEDE_NO_EXISTE);

        String username = solicitudCrearUsuario.username().replaceAll("\\s", "");
        String contrasena = solicitudCrearUsuario.password().replaceAll("\\s", "");

        // El nombre de usuario contiene solo alfanuméricos y _
        if (username.length() < 4 || username.matches("[^a-zA-Z0-9_]")) {
            return ResCrearUsuario.Error(ResUsuarioErrorCode.NOMBRE_USUARIO_INVALIDO);
        }

        // La contraseña posee únicamente caracteres alfanuméricos
        if (contrasena.length() < 8 || !contrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+$"))
            return ResCrearUsuario.Error(ResUsuarioErrorCode.CONTRASENA_INVALIDA);

        try {
            Usuario nuevoUsuario = Usuario.builder()
                    .username(solicitudCrearUsuario.username())
                    .password(passwordEncoder.encode(solicitudCrearUsuario.password()))
                    .sede(sede)
                    .build();

            return ResCrearUsuario.builder()
                    .usuario(usuarioRepository.save(nuevoUsuario))
                    .build();
        }
        catch (DataIntegrityViolationException exception) {
            return ResCrearUsuario.Error(ResUsuarioErrorCode.USUARIO_YA_EXISTE);
        }
    }

    @Auditable(value = TipoAccion.CONSULTAR, nombreEntidad = "Usuario", detalles = "Consultar usuarios")
    public List<UsuarioTablaDTO> mostrarUsuariosEnTabla() {
        return usuarioRepository.findAllUsuarioTablaDTO();
    }

    @Auditable(value = TipoAccion.EDITAR, nombreEntidad = "Usuario", detalles = "Cambiar contraseña")
    public ResCambiarContraErrorCode cambiarContra(SolicitudCambiarContra solicitudCambiarContra) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        if (!passwordEncoder.matches(solicitudCambiarContra.contraActual(), usuario.getPassword()))
            return ResCambiarContraErrorCode.CONTRA_ACTUAL_INVALIDA;

        String nuevaContra = solicitudCambiarContra.nuevaContra().replaceAll("\\s", "");

        if (nuevaContra.length() < 8)
            return ResCambiarContraErrorCode.LONGITUD_INVALIDA;

        if (!nuevaContra.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+$"))
            return ResCambiarContraErrorCode.FORMATO_INVALIDO;

        usuario.setPassword(passwordEncoder.encode(solicitudCambiarContra.nuevaContra()));
        usuarioRepository.save(usuario);

        return ResCambiarContraErrorCode.NO_ERROR;
    }

}
