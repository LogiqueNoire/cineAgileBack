salas_id = [
101
, 102
, 103
, 104
, 105
, 106
, 107
, 108
, 201
, 202
, 203
, 204
, 205
, 206
, 207
, 301
, 302
, 303
, 304
, 305
, 306
, 401
, 402
, 403
, 404
, 405
, 406
, 407
, 408
, 409
, 410
, 501
, 502
, 503
, 504
, 505
, 601
, 602
, 603
, 604
, 605
, 606
, 701
, 702
, 703
, 704
, 705
, 801
, 802
, 803
, 804
, 805
, 806
, 901
, 902
, 903
, 904
, 1001
, 1002
, 1003
, 1004
, 1005
, 1101
, 1102
, 1103
, 1104
, 1201
, 1202
, 1203
, 1204
, 1205
, 1301
, 1302
, 1303
, 1401
, 1402
, 1403
, 1404
, 1501
, 1502
, 1503
, 1601
, 1602
, 1701
, 1702
, 1703
, 1801
, 1802
, 1901
, 1902
, 1903
, 2001
, 2002
, 2003
, 2004
]

salas_prime_ids = [
    106
,108
,203
,207
,304
,403
,406
,410
,504
,603
,703
,803
,903
,1004
,1103
,1203
,1303
,1403
,1503
,1702
,1802
,1903
,2003
]

peliculas_ids = [
41847,
88138,
44659,
53688,
81399,
82580,
14109,
51392,
83802,
4225, 
90428,
84655,
38061,
1398, 
1162, 
17148,
8157, 
54033,
15334,
79521
]

from datetime import datetime, date, timedelta, timezone, time
import pytz
from random import random

fecha_inicio = date.today()
hora_inicio = datetime.now()
mi_tz = pytz.timezone("America/Lima")
utc_tz = pytz.timezone("UTC")

inicio = 0
fin = 14

hora_inicio = 8
hora_fin = 20

def get_funciones_diarias():
    return 1 + int(random() * 3)

query = "INSERT INTO \"cine-dev\".funcion (id, dimension, fecha_hora_inicio, fecha_hora_fin, precio_base, id_pelicula, id_sala) VALUES\n"

contador = 0
dimensiones = [ "2D", "3D" ]
decimals = [ 0.20, 0.40, 0.5, 0.75, 0.8 ]

for sala_id in salas_id:
    for dia in range(inicio, fin):
        fecha = fecha_inicio + timedelta(dia)
        fecha_hora_del_dia = datetime.combine(fecha, datetime.min.time()) + timedelta(hours=8) # Convertir a UTC

        while fecha_hora_del_dia.hour <= hora_fin:
            duracion = ((int(random() * 3) + 1) * 30) + 60.0
            random_pelicula_id = peliculas_ids[int(random() * len(peliculas_ids))]
            
            fecha_hora_del_dia_utc = fecha_hora_del_dia #mi_tz.localize(fecha_hora_del_dia).astimezone(utc_tz)
            fh_inicio = fecha_hora_del_dia_utc
            fh_fin = fecha_hora_del_dia_utc + timedelta(minutes=duracion)

            id = contador
            dimension = dimensiones[int(random() * 2)]
            fecha_hora_inicio = fh_inicio.isoformat()
            fecha_hora_fin = fh_fin.isoformat()
            precio_base = int(11 + random() * 20) + decimals[int(random() * len(decimals))]
            id_pelicula = random_pelicula_id
            id_sala = sala_id

            query += f"( {id}, '{dimension}', '{fecha_hora_inicio}', '{fecha_hora_fin}', {precio_base}, {id_pelicula}, {id_sala} ),\n"
            contador += 1

            fecha_hora_del_dia += timedelta(minutes=(duracion + 20))


query = query[: len(query) - 2] + ";"

with open("funciones_inserts.sql", "w") as f:
    f.write(query)

# Dias restantes de la semana
# for i in range(inicio, fin):
#     fecha = fecha_inicio + timedelta(i)
#     fecha_hora = datetime.combine(fecha, datetime.min.time()) + timedelta(hours=8)
# 
#     for peli_id in peliculas_ids:
#         for sala_id in salas_id:
#             funciones_por_sala = int(random() * 2)
#             
#             for j in range(0, funciones_por_sala):
#                 id = contador + 100000
#                 dimension = dimensiones[int(random() * 2)]
#                 fecha_hora_inicio = fecha_hora + timedelta(hours=int(random() * 12))
#                 fecha_hora_fin = fecha_hora_inicio + timedelta(hours=1, minutes=45)
#                 precio_base = int(11 + random() * 20) + decimals[int(random() * len(decimals))]
#                 id_pelicula = peli_id
#                 id_sala = sala_id
# 
#                 query += f"( {id}, '{dimension}', '{fecha_hora_inicio.isoformat()}', '{fecha_hora_fin.isoformat()}', {precio_base}, {id_pelicula}, {id_sala} ),\n"
# 
#                 contador += 1
# 
# query = query[: len(query) - 2] + ";"

# with open("funciones_inserts.sql", "w") as f:
#     f.write(query)