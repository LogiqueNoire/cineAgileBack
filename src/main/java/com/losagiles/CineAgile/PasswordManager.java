package com.losagiles.CineAgile;

import com.losagiles.CineAgile.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordManager implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        /*
            Descomentar o comentar las dos líneas de código para reestablecer
            o no la contraseña.
         */

        authService.establecerContra("Hola12345");
    }
}