-- Sedes

INSERT INTO "cine-dev".sede (id, activo, nombre) VALUES
(1, TRUE, 'CineAgile Lima - San Isidro'),
(2, TRUE, 'CineAgile Lima - Miraflores'),
(3, TRUE, 'CineAgile Lima - MegaPlaza'),
(4, TRUE, 'CineAgile Lima - Jockey Plaza'),
(5, TRUE, 'CineAgile Arequipa - Yanahuara'),
(6, TRUE, 'CineAgile Arequipa - Mall Aventura'),
(7, TRUE, 'CineAgile Trujillo - Real Plaza'),
(8, TRUE, 'CineAgile Trujillo - Mall Aventura'),
(9, TRUE, 'CineAgile Cusco - Centro Histórico'),
(10,TRUE,  'CineAgile Cusco - Real Plaza'),
(11,TRUE,  'CineAgile Piura - Open Plaza'),
(12,TRUE,  'CineAgile Chiclayo - Mall Aventura'),
(13,TRUE,  'CineAgile Iquitos - Plaza 28 de Julio'),
(14,TRUE,  'CineAgile Huancayo - Real Plaza'),
(15,TRUE,  'CineAgile Tacna - Centro Cívico'),
(16,TRUE,  'CineAgile Puno - Plaza Mayor'),
(17,TRUE,  'CineAgile Juliaca - Plaza San Román'),
(18,TRUE,  'CineAgile Huaraz - Plaza de Armas'),
(19,TRUE,  'CineAgile Tarapoto - Centro'),
(20,TRUE,  'CineAgile Cajamarca - El Quinde');

-- Salas

INSERT INTO "cine-dev".sala (id_sede, activo, id, categoria, codigo_sala) VALUES
-- Lima - San Isidro (id_sede = 1, 8 salas)
(1, TRUE, 101, 'Regular', 'S1'),
(1, TRUE, 102, 'Regular', 'S2'),
(1, TRUE, 103, 'Regular', 'S3'),
(1, TRUE, 104, 'Regular', 'S4'),
(1, TRUE, 105, 'Regular', 'S5'),
(1, TRUE, 106, 'Prime',   'S6'),
(1, TRUE, 107, 'Regular', 'S7'),
(1, TRUE, 108, 'Prime',   'S8'),

-- Lima - Miraflores (id_sede = 2, 7 salas)
(2,TRUE, 201, 'Regular', 'S1'),
(2,TRUE, 202, 'Regular', 'S2'),
(2,TRUE, 203, 'Prime',   'S3'),
(2,TRUE, 204, 'Regular', 'S4'),
(2,TRUE, 205, 'Regular', 'S5'),
(2,TRUE, 206, 'Regular', 'S6'),
(2,TRUE, 207, 'Prime',   'S7'),

-- Lima - MegaPlaza (id_sede = 3, 6 salas)
(3,TRUE, 301, 'Regular', 'S1'),
(3,TRUE, 302, 'Regular', 'S2'),
(3,TRUE, 303, 'Regular', 'S3'),
(3,TRUE, 304, 'Prime',   'S4'),
(3,TRUE, 305, 'Regular', 'S5'),
(3,TRUE, 306, 'Regular', 'S6'),

-- Lima - Jockey Plaza (id_sede = 4, 10 salas)
(4,TRUE, 401, 'Regular', 'S1'),
(4,TRUE, 402, 'Regular', 'S2'),
(4,TRUE, 403, 'Prime',   'S3'),
(4,TRUE, 404, 'Regular', 'S4'),
(4,TRUE, 405, 'Regular', 'S5'),
(4,TRUE, 406, 'Prime',   'S6'),
(4,TRUE, 407, 'Regular', 'S7'),
(4,TRUE, 408, 'Regular', 'S8'),
(4,TRUE, 409, 'Regular', 'S9'),
(4,TRUE, 410, 'Prime',   'S10'),

-- Arequipa - Yanahuara (id_sede = 5, 5 salas)
(5,TRUE, 501, 'Regular', 'S1'),
(5,TRUE, 502, 'Regular', 'S2'),
(5,TRUE, 503, 'Regular', 'S3'),
(5,TRUE, 504, 'Prime',   'S4'),
(5,TRUE, 505, 'Regular', 'S5'),

-- Arequipa - Mall Aventura (id_sede = 6, 6 salas)
(6,TRUE, 601, 'Regular', 'S1'),
(6,TRUE, 602, 'Regular', 'S2'),
(6,TRUE, 603, 'Prime',   'S3'),
(6,TRUE, 604, 'Regular', 'S4'),
(6,TRUE, 605, 'Regular', 'S5'),
(6,TRUE, 606, 'Regular', 'S6'),

-- Trujillo - Real Plaza (id_sede = 7, 5 salas)
(7,TRUE, 701, 'Regular', 'S1'),
(7,TRUE, 702, 'Regular', 'S2'),
(7,TRUE, 703, 'Prime',   'S3'),
(7,TRUE, 704, 'Regular', 'S4'),
(7,TRUE, 705, 'Regular', 'S5'),

-- Trujillo - Mall Aventura (id_sede = 8, 6 salas)
(8,TRUE, 801, 'Regular', 'S1'),
(8,TRUE, 802, 'Regular', 'S2'),
(8,TRUE, 803, 'Prime',   'S3'),
(8,TRUE, 804, 'Regular', 'S4'),
(8,TRUE, 805, 'Regular', 'S5'),
(8,TRUE, 806, 'Regular', 'S6'),

-- Cusco - Centro Histórico (id_sede = 9, 4 salas)
(9,True, 901, 'Regular', 'S1'),
(9,True, 902, 'Regular', 'S2'),
(9,True, 903, 'Prime',   'S3'),
(9,True, 904, 'Regular', 'S4'),

-- Cusco - Real Plaza (id_sede = 10, 5 salas)
(10,TRUE, 1001, 'Regular', 'S1'),
(10,TRUE, 1002, 'Regular', 'S2'),
(10,TRUE, 1003, 'Regular', 'S3'),
(10,TRUE, 1004, 'Prime',   'S4'),
(10,TRUE, 1005, 'Regular', 'S5'),

-- Piura - Open Plaza (id_sede = 11, 4 salas)
(11,TRUE, 1101, 'Regular', 'S1'),
(11,TRUE, 1102, 'Regular', 'S2'),
(11,TRUE, 1103, 'Prime',   'S3'),
(11,TRUE, 1104, 'Regular', 'S4'),

-- Chiclayo - Mall Aventura (id_sede = 12, 5 salas)
(12,TRUE, 1201, 'Regular', 'S1'),
(12,TRUE, 1202, 'Regular', 'S2'),
(12,TRUE, 1203, 'Prime',   'S3'),
(12,TRUE, 1204, 'Regular', 'S4'),
(12,TRUE, 1205, 'Regular', 'S5'),

-- Iquitos - Plaza 28 de Julio (id_sede = 13, 3 salas)
(13,TRUE, 1301, 'Regular', 'S1'),
(13,TRUE, 1302, 'Regular', 'S2'),
(13,TRUE, 1303, 'Prime',   'S3'),

-- Huancayo - Real Plaza (id_sede = 14, 4 salas)
(14,TRUE, 1401, 'Regular', 'S1'),
(14,TRUE, 1402, 'Regular', 'S2'),
(14,TRUE, 1403, 'Prime',   'S3'),
(14,TRUE, 1404, 'Regular', 'S4'),

-- Tacna - Centro Cívico (id_sede = 15, 3 salas)
(15,TRUE, 1501, 'Regular', 'S1'),
(15,TRUE, 1502, 'Regular', 'S2'),
(15,TRUE, 1503, 'Prime',   'S3'),

-- Puno - Plaza Mayor (id_sede = 16, 2 salas)
(16,TRUE, 1601, 'Regular', 'S1'),
(16,TRUE, 1602, 'Regular', 'S2'),

-- Juliaca - Plaza San Román (id_sede = 17, 3 salas)
(17,TRUE, 1701, 'Regular', 'S1'),
(17,TRUE, 1702, 'Prime',   'S2'),
(17,TRUE, 1703, 'Regular', 'S3'),

-- Huaraz - Plaza de Armas (id_sede = 18, 2 salas)
(18,TRUE, 1801, 'Regular', 'S1'),
(18,TRUE, 1802, 'Prime',   'S2'),

-- Tarapoto - Centro (id_sede = 19, 3 salas)
(19,TRUE, 1901, 'Regular', 'S1'),
(19,TRUE, 1902, 'Regular', 'S2'),
(19,TRUE, 1903, 'Prime',   'S3'),

-- Cajamarca - El Quinde (id_sede = 20, 4 salas)
(20,TRUE, 2001, 'Regular', 'S1'),
(20,TRUE, 2002, 'Regular', 'S2'),
(20,TRUE, 2003, 'Prime',   'S3'),
(20,TRUE, 2004, 'Regular', 'S4');
