import requests
import random

upcoming_url = "https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1"
now_playing_url = "https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1"
token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzN2IwM2IzNDhlZTc1MjgxYThjNTI5MDQxNWZkZTk0MyIsIm5iZiI6MTc0NTgxMDI3MS4xNDcsInN1YiI6IjY4MGVmMzVmMTBkMWU0NTNkMmViMjAyOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.aPZxIolUwBlz0AruYDlyRSN4jeycw7L4GN8N27tQl68"
headers = {
    "Authorization": token
}

def create_extract_movie_info_mapper(estado):
    def extract_movie_info_mapper(movie):
        return {
            "id_pelicula": int(random.random() * 100000),
            "actores": " ",
            "clasificacion": " ",
            "director": " ",
            "duracion": 120,
            "estado": estado,
            "fecha_inicio_estreno":movie["release_date"],
            "genero": " ",
            "nombre": movie["title"],
            "sipnosis": movie["overview"],
            "image_url": f'https://image.tmdb.org/t/p/w500/{movie["poster_path"]}'
        }
    return extract_movie_info_mapper

def fetch_movies(api_url, state=""):
    response = requests.get(api_url, headers=headers)
    mapper = create_extract_movie_info_mapper(state)

    if response.status_code == 200:
        upcoming = response.json()
        resultados = list(map(mapper, upcoming['results']))

        query = ""
        for idx, item in enumerate(resultados):
            coma_exterior = ')' if idx == len(resultados) - 1 else '),\n'
            query += "("
            for jdx, key in enumerate(item):
                valor = item[key]
                comillas = ''
                if (type(valor) is str):
                    comillas = "'"
                    valor = valor.replace("'", "''")
                    valor = valor[0:253]
                coma = ',' if jdx < len(item) - 1 else ''
                query += f"{comillas}{valor}{comillas}{coma} "
            query += coma_exterior
        
        return query
    else:
        return f'Error: {response.status_code}'

with open("peliculas_inserts.sql", "w", encoding="UTF-8") as archivo:
    cabecera = "INSERT INTO \"cine-dev\".pelicula ( id_pelicula, actores, clasificacion, director, duracion, estado, fecha_inicio_estreno, genero, nombre, sinopsis, image_url) VALUES\n"
    now_playing_values = fetch_movies(now_playing_url, "en cartelera")
    upcoming_values = fetch_movies(upcoming_url, "proximamente")

    archivo.write(cabecera)
    archivo.writelines(f'{now_playing_values},\n')
    archivo.writelines(f'{upcoming_values};')
    archivo.close()