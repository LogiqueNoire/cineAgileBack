package com.losagiles.CineAgile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@Service
public class ScriptService {

    @Autowired
    private DataSource dataSource;

    private static final List<String> SCRIPTS_PERMITIDOS = List.of(
            "generos_query.sql",
            "sedes_salas_inserts_query.sql",
            "peliculas_inserts_query.sql",
            "butacas_query.sql",
            "actualizar_ids.sql"
    );
/*
    public void ejecutarScript(String nombreScript) throws Exception {
        if(!SCRIPTS_PERMITIDOS.contains(nombreScript)){
            throw new IllegalArgumentException("Scrip no permitido");
        }
        try (Connection connection = dataSource.getConnection()){
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("/sql/"+nombreScript));
        }
    }
*/
    public void poblarBD() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            for(String s: SCRIPTS_PERMITIDOS) {
                try {
                    ScriptUtils.executeSqlScript(connection, new ClassPathResource("/dbscripts/" + s));
                    System.out.println("Script ejecutado correctamente: " + s);
                } catch (Exception e) {
                    System.err.println("Error en script " + s + ": " + e.getMessage());
                    throw e;
                }
            }
        }
    }

    public void reiniciarBD() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            try {
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("/dbscripts/" + "truncateTables.sql"));
                System.out.println("Script ejecutado correctamente: " + "truncateTables.sql");
            } catch (Exception e) {
                System.err.println("Error en script " + "truncateTables.sql" + ": " + e.getMessage());
                throw e;
            }
        }
    }
}