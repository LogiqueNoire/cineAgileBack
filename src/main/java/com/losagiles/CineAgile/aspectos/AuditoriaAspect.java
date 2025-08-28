package com.losagiles.CineAgile.aspectos;

import com.losagiles.CineAgile.entidades.RegistroAccion;
import com.losagiles.CineAgile.otros.Auditable;
import com.losagiles.CineAgile.otros.TipoAccion;
import com.losagiles.CineAgile.repository.RegistroAccionRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

@Aspect
@Component
public class AuditoriaAspect {

    private final RegistroAccionRepository registroAccionRepository;

    @Autowired
    public AuditoriaAspect(RegistroAccionRepository registroAccionRepository) {
        this.registroAccionRepository = registroAccionRepository;
    }

    // Aspecto que captura cualquier método anotado con @Auditable
    @AfterReturning(pointcut = "@annotation(auditable)", returning = "result")
    public void auditar(JoinPoint joinPoint, Auditable auditable, Object result) {
        TipoAccion tipo = auditable.value();
        String nombreEntidad = auditable.nombreEntidad();
        String detalles = auditable.detalles();
        Long idEntidad = null;

        // Intentamos obtener getId() si existe
        if (result != null) {
            try {
                Method getId = result.getClass().getMethod("getId");
                idEntidad = (Long) getId.invoke(result);
            } catch (Exception ignored) {}
        }

        String usuario = SecurityContextHolder.getContext().getAuthentication() != null ?
                SecurityContextHolder.getContext().getAuthentication().getName() : "anonimo";

        RegistroAccion registroAccion = new RegistroAccion();
        registroAccion.setUsuario(usuario);
        registroAccion.setAccion(tipo.name());
        registroAccion.setEntidadAfectada(nombreEntidad);
        registroAccion.setIdEntidad(idEntidad);
        registroAccion.setDetalles(detalles);
        registroAccion.setIp(""); // o obtener dinámicamente si tienes HttpServletRequest

        registroAccionRepository.save(registroAccion);
    }
}
