-- auditoria
SELECT setval(pg_get_serial_sequence('"cine-dev".auditoria', 'id'),
              COALESCE((SELECT MAX(id) FROM "cine-dev".auditoria), 1), true);

-- butaca
SELECT setval(pg_get_serial_sequence('"cine-dev".butaca', 'id'),
              COALESCE((SELECT MAX(id) FROM "cine-dev".butaca), 1), true);

-- funcion
SELECT setval(pg_get_serial_sequence('"cine-dev".funcion', 'id'),
              COALESCE((SELECT MAX(id) FROM "cine-dev".funcion), 1), true);

-- genero
SELECT setval(pg_get_serial_sequence('"cine-dev".genero', 'id'),
              COALESCE((SELECT MAX(id) FROM "cine-dev".genero), 1), true);

-- pelicula
SELECT setval(pg_get_serial_sequence('"cine-dev".pelicula', 'id_pelicula'),
              COALESCE((SELECT MAX(id_pelicula) FROM "cine-dev".pelicula), 1), true);

-- sala
SELECT setval(pg_get_serial_sequence('"cine-dev".sala', 'id'),
              COALESCE((SELECT MAX(id) FROM "cine-dev".sala), 1), true);

-- sede
SELECT setval(pg_get_serial_sequence('"cine-dev".sede', 'id'),
              COALESCE((SELECT MAX(id) FROM "cine-dev".sede), 1), true);

-- usuario
SELECT setval(pg_get_serial_sequence('"cine-dev".usuario', 'id'),
              COALESCE((SELECT MAX(id) FROM "cine-dev".usuario), 1), true);
