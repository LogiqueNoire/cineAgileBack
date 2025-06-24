package com.losagiles.CineAgile.rest;

import com.losagiles.CineAgile.dto.*;
import com.losagiles.CineAgile.entidades.*;
import com.losagiles.CineAgile.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/intranet")
public class IntranetController {
    @Autowired
    PeliculaService peliculaService;

    @Autowired
    SedeService sedeService;

    @Autowired
    SalaButacasService salaButacasService;

    @Autowired
    FuncionService funcionService;

    @Autowired
    SalaService salaService;

    @Autowired
    UsuarioInternoService usuarioInternoService;

    @Autowired
    GeneroService generoService;

    @GetMapping("/peliculas")
    public List<PeliculaDTO> obtenerPeliculasConEstado(@RequestParam String fechaReal) {
        LocalDateTime fecha = LocalDateTime.parse(fechaReal.replace("Z", ""));
        System.out.println(fecha);
        return peliculaService.obtenerPeliculasConEstado(fecha);
    }

    @GetMapping("/soloPeliculas")
    public List<Pelicula> findAll() {
        return peliculaService.findAll();
    }


    @PostMapping("/peliculas/agregar")
    private ResponseEntity<Pelicula> addPelicula(@RequestBody PeliculaDTO dto) {
        Pelicula pelicula = new Pelicula();

        pelicula.setNombre(dto.getNombre());
        pelicula.setDirector(dto.getDirector());
        pelicula.setActores(dto.getActores());

        List<Long> generoIds = dto.getGenero().stream()
                .map(Genero::getId)
                .collect(Collectors.toList());

        List<Genero> generos = generoService.findAllById(generoIds);
        pelicula.setGenero(generos);

        pelicula.setClasificacion(dto.getClasificacion());
        pelicula.setDuracion(dto.getDuracion());

        LocalDate fechaEstreno = LocalDate.parse(dto.getFechaInicioEstreno());
        pelicula.setFechaInicioEstreno(fechaEstreno);

        pelicula.setImageUrl(dto.getImageUrl());
        pelicula.setSinopsis(dto.getSinopsis());

        peliculaService.agregarPelicula(pelicula);
        return ResponseEntity.ok(pelicula);
    }

    @GetMapping("/sedesTodas")
    public List<?> getSedes() {
        return sedeService.getNombresSedes();
    }

    @PostMapping("/sedesysalas/agregar")
    private ResponseEntity<?> addSede(@RequestBody SedeDTO dto) {
        if (sedeService.existsByNombre(dto.getNombre())) {
            return ResponseEntity.status(409).body("Sede repetida");
        } else {
            Sede sede = new Sede();
            sede.setNombre(dto.getNombre());
            sede.setActivo(true);
            sedeService.save(sede);

            return ResponseEntity.ok(sede);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated())
            return ResponseEntity.status(401).body("no autenticado");

        return ResponseEntity.ok((String) auth.getPrincipal());
    }

    @GetMapping ("/soloSedes")
    private ResponseEntity<List<NombreDTO>> getNombresSedesActivas(){
        return ResponseEntity.ok(sedeService.getNombresSedesActivas());
    }

    @GetMapping ("/peliculasPorSede")
    private ResponseEntity<List<NombreDTO>> getNombresPeliculas(@RequestParam Long idSede){
        return ResponseEntity.ok(peliculaService.getNombresPeliculas(idSede));
    }

    @GetMapping ("/salasPorSede")
    private ResponseEntity<List<Sala>> getSalasPorSede(@RequestParam Long idSede){
        return ResponseEntity.ok(salaButacasService.getSalasPorSede(idSede));
    }

    @GetMapping ("/buscarFuncionesPorSemanaConPelicula")
    private ResponseEntity<List<FuncionDTO>>
    buscarFuncionesPorSemanaConPelicula(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
            @RequestParam Long idPelicula,
            @RequestParam Long idSede) {
        return ResponseEntity.ok(funcionService.buscarFuncionesPorSemanaConPelicula(fecha, idPelicula, idSede));
    }

    @GetMapping ("/buscarFuncionesPorSemanaConSala")
    private ResponseEntity<List<FuncionDTO>>
    buscarFuncionesPorSemanaConSala(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
            @RequestParam Long idSala,
            @RequestParam Long idSede){
        return ResponseEntity.ok(funcionService.buscarFuncionesPorSemanaConSala(fecha, idSala, idSede));
    }


    @PostMapping("/crearsala")
    public ResponseEntity<String> crearSala(@RequestBody SolicitudCrearSala solicitudCrearSala) {
        Sede sede = sedeService.findById(solicitudCrearSala.idSede()).get();
        if(salaService.existsBySedeAndCodigoSala(sede, solicitudCrearSala.codigoSala())){
            return ResponseEntity.status(409).body("Sala repetida");
        } else {

            ResCrearSala resSala = salaService.crearSala(solicitudCrearSala);

            if (resSala.error() != null) {
                if (resSala.error() == ResSalaErrorCode.EXCEPCION_INTEGRACION_DATOS)
                    return ResponseEntity
                        .status(409)
                        .body(resSala.error().getDescripcion());
                if (resSala.error() == ResSalaErrorCode.MAX_FILA_SOBREPASADA
                    || resSala.error() == ResSalaErrorCode.BUTACAS_NO_ENCONTRADAS) {
                    return ResponseEntity
                        .status(422)
                        .body(resSala.error().getDescripcion());
                }
            }
            return ResponseEntity.ok().build();
        }
    }

    @PatchMapping("/actualizarFuncion")
    public ResponseEntity<?> actualizarFuncion(@RequestBody FuncionDTO funcionDTO){
        if(funcionDTO.getIdFuncion() == null ||
                funcionDTO.getFechaHoraInicio() == null ||
                funcionDTO.getDimension () == null ||
                funcionDTO.getPrecioBase() == 0 ||
                funcionDTO.getIdSala() == null ||
                funcionDTO.getIdPelicula() == null){
            return ResponseEntity.status(400).body("Datos incompletos");
        }
        Optional<?> actualizada = funcionService.actualizarFuncion(funcionDTO);
        return actualizada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Función no encontrada"));
    }

    @PatchMapping("/editarSede")
    public ResponseEntity<?> editarSede(@RequestBody NombreDTO sedeDTO){
        Optional<Sede> actualizada = sedeService.editarSede(sedeDTO);

        if (actualizada.isEmpty()) {
            return ResponseEntity.status(409).body("Nombre de sede ya está en uso");
        }

        return actualizada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crearFuncion")
    public ResponseEntity<?> save(@RequestBody FuncionDTO funcionDTO){
        if(funcionDTO.getIdFuncion() == null ||
                funcionDTO.getFechaHoraInicio() == null ||
                funcionDTO.getDimension () == null ||
                funcionDTO.getPrecioBase() == 0 ||
                funcionDTO.getIdSala() == null ||
                funcionDTO.getIdPelicula() == null){
            return ResponseEntity.status(400).body("Datos incompletos");
        }
        Optional<Funcion> funcion = funcionService.save(funcionDTO);
        return funcion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<UsuarioTablaDTO>> getUsuarios() {
        return ResponseEntity.ok(usuarioInternoService.mostrarUsuariosEnTabla());
    }

    @PostMapping("/usuario")
    public ResponseEntity<String> crearUsuario(@RequestBody SolicitudCrearUsuario solicitudCrearUsuario) {
        ResCrearUsuario res = usuarioInternoService.crearUsuario(solicitudCrearUsuario);

        if (res.error() != null) {
            if (res.error() == ResUsuarioErrorCode.NOMBRE_USUARIO_INVALIDO ||
                    res.error() == ResUsuarioErrorCode.CONTRASENA_INVALIDA ||
                    res.error() == ResUsuarioErrorCode.SEDE_NO_EXISTE) {
                return ResponseEntity.status(422).body(res.error().getDescripcion());
            }

            if (res.error() == ResUsuarioErrorCode.USUARIO_YA_EXISTE)
                return ResponseEntity.status(409).body(res.error().getDescripcion());
        }

        return ResponseEntity.status(201).body("¡Usuario creado con exito!");
    }

    @PutMapping("/nuevacontra")
    public ResponseEntity<String> setNuevaContra(@RequestBody SolicitudCambiarContra solicitudCambiarContra) {
        ResCambiarContraErrorCode res = usuarioInternoService.cambiarContra(solicitudCambiarContra);

        if (res != null) {
            if (res == ResCambiarContraErrorCode.CONTRA_ACTUAL_INVALIDA ||
            res == ResCambiarContraErrorCode.LONGITUD_INVALIDA ||
            res == ResCambiarContraErrorCode.FORMATO_INVALIDO)
                return ResponseEntity.status(422).body(res.getDescripcion());
        }

        return ResponseEntity.ok("Se cambió la contraseña.");
    }

    @PatchMapping("/sala")
    public ResponseEntity<String> patchSala(@RequestBody SolicitudEditarSala solicitudEditarSala) {
        ResEditarSalaResultCode res = salaService.editarSala(solicitudEditarSala);

        if (res != ResEditarSalaResultCode.NO_ERROR)
            return ResponseEntity.status(422).body(res.getDescripcion());

        return ResponseEntity.ok(res.getDescripcion());
    }

    @PatchMapping("activarDesactivarSede")
    public ResponseEntity<?> activarDesactivar(@RequestBody NombreDTO dto){
        Sede sede = sedeService.findById(dto.getId()).get();
        if (sede != null) {
            sede.setActivo(!sede.getActivo());
            sedeService.save(sede);
            return ResponseEntity.ok("Sede cambiada");
        } else {
            return ResponseEntity.status(422).body("Error al editar");
        }
    }

    @PatchMapping("/sala/cambiar-estado/{id}")
    public ResponseEntity<String> patchSalaCambiarEstado(@PathVariable Long id, @RequestBody NombreDTO nombreDTO) {
        Sala sala = salaService.establecerEstadoSala(id, nombreDTO.getActivo());

        if (sala == null)
            return ResponseEntity.status(404).body("Sala no encontrada.");

        return ResponseEntity.ok("Estado de sala " + id + " cambiado a " + nombreDTO.getActivo() + "!");
    }

    @PatchMapping("/pelicula")
    public ResponseEntity<String> patchPelicula(@RequestBody PatchPeliculaRequest patchPeliculaRequest) {
        PatchPeliculaStatus status = peliculaService.editarPelicula(patchPeliculaRequest);
        return ResponseEntity.status(status.getHttpStatus()).body(status.getDescripcion());
    }

    @GetMapping("/generos")
    public ResponseEntity<List<Genero>> getGeneros(){
        return ResponseEntity.ok(generoService.findAll());
    }

    @PostMapping("/generos/agregar")
    public ResponseEntity<?> saveGenero(@RequestBody String nombre){
        Genero g = generoService.save(nombre);
        if(g != null){
            return ResponseEntity.ok(g);
        }
        return ResponseEntity.status(422).body("Error al guardar");
    }

    @PatchMapping("/generos/editar")
    public ResponseEntity<?> editarGenero(@RequestBody NombreDTO dto){
        Genero g = generoService.editar(dto);
        if(g != null){
            return ResponseEntity.ok(g);
        }
        return ResponseEntity.status(422).body("Error al guardar");
    }

}
