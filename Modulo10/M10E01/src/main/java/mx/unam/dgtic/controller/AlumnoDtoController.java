package mx.unam.dgtic.controller;

import jakarta.validation.Valid;
import mx.unam.dgtic.dto.AlumnoDto;
import mx.unam.dgtic.exception.EstadoNoExisteException;
import mx.unam.dgtic.model.Alumno;
import mx.unam.dgtic.servicio.AlumnoDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v2/alumnos")
public class AlumnoDtoController {

    @Autowired
    AlumnoDtoService alumnoDtoService;

    //obtener todos
    @GetMapping(path = "/")
    public List<AlumnoDto> getAllDto(){
        return alumnoDtoService.getAlumnosList();
    }

    @GetMapping(path = "/estados/{edo}")
    public ResponseEntity<List<AlumnoDto>> getByEstado(@PathVariable String edo){
        return ResponseEntity.ok(alumnoDtoService.findAlumnosByEstados(edo));
    }


    //obtener por matricula
    @GetMapping(path = "/{matricula}")
    public ResponseEntity<AlumnoDto> getByIdDto(@PathVariable String matricula){
        Optional<AlumnoDto> alumnoDto = alumnoDtoService.getAlumnoById(matricula);

        if(alumnoDto.isPresent()){
            return ResponseEntity.ok(alumnoDto.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //crear alumno
    @PostMapping(path="/")
    public ResponseEntity<AlumnoDto> createdAlumnoDto(
            @Valid @RequestBody AlumnoDto alumnoDto) throws ParseException, URISyntaxException, EstadoNoExisteException {
        AlumnoDto alumnoDto1 = alumnoDtoService.createAlumno(alumnoDto);
        URI location = new URI("/api/v2/alumnos/"+ alumnoDto1.getMatricula());
        return ResponseEntity.created(location).body(alumnoDto1);
    }

    //
    @PutMapping(path = "/{matricula}")
    public ResponseEntity<AlumnoDto> modificarAlumno(
            @PathVariable String matricula,
            @RequestBody AlumnoDto alumnoDto
    ) throws ParseException, EstadoNoExisteException {
        alumnoDto.setMatricula(matricula);
        AlumnoDto alumnoDtoModificado = alumnoDtoService.updateAlumno(alumnoDto);
        return ResponseEntity.ok(alumnoDtoModificado);
    }

    //Actualizacion Parcial
    @PatchMapping(path = "/{matricula}")
    public ResponseEntity<AlumnoDto> acualizacionParcialAlumno(@PathVariable String matricula,
                                                               @RequestBody AlumnoDto alumnoDto) throws ParseException, EstadoNoExisteException {

        Optional<AlumnoDto> alumnoDb = alumnoDtoService.getAlumnoById(matricula);

        if(alumnoDb.isPresent()){
            AlumnoDto modificable = alumnoDb.get();
            if(alumnoDto.getNombre() != null) modificable.setNombre(alumnoDto.getNombre());
            if(alumnoDto.getPaterno() != null) modificable.setPaterno(alumnoDto.getPaterno());
            if(alumnoDto.getFnac() != null) modificable.setFnac(alumnoDto.getFnac());
            if(alumnoDto.getEstatura() != 0.0) modificable.setEstatura(alumnoDto.getEstatura());
            if(alumnoDto.getEstado() != null) modificable.setEstado(alumnoDto.getEstado());

            return ResponseEntity.ok(alumnoDtoService.updateAlumno(modificable));

        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{matricula}")
    public ResponseEntity<?> eliminarAlumno(@PathVariable String matricula){
        if(alumnoDtoService.deleteAlumno(matricula)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // /api/v2/alumnos/paginado?page=0&size=2&dir=asc&sort=nombre
    @GetMapping("/paginado")
    public ResponseEntity<List<Alumno>> getPaginadoAlumno(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "2") int size,
                                                          @RequestParam(defaultValue = "asc") String dir,
                                                          @RequestParam(defaultValue = "matricula") String sort){


        return ResponseEntity.ok(alumnoDtoService.getAlumnosPageableList(
                page,
                size,
                dir,
                sort
        ));
    }

    @GetMapping(path="/ping/{veces}")
    public ResponseEntity<String> ping(@PathVariable int veces){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i< veces; i++){
            str.append("\n TTL "+ i++);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.TEXT_PLAIN)
                .body(str.toString());
    }

    /*@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> errorFormatoDeCliente(
            HttpMessageNotReadableException ex
    ){
        HashMap<String, String> detalles = new HashMap<>();
        detalles.put("mensaje", "El formato de los datos es incorrecto");
        detalles.put("detalle", ex.getMessage());
        detalles.put("timestamp", LocalDateTime.now().toString());

        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(detalles);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratamientoValidacion(
            MethodArgumentNotValidException ex
    ){
        HashMap<String, Object> detalles = new HashMap<>();
        detalles.put("mensaje", "Error de validacion de campos, favor de revisar");
        detalles.put("statusCode", ex.getStatusCode());
        detalles.put("timestamp", LocalDateTime.now().toString());

        HashMap<String, String> detalleCampos = new HashMap<>();

        int i = 1;
        for(FieldError campoError : ex.getBindingResult().getFieldErrors()){
            //Nombre errrr, detalles error
            detalleCampos.put(campoError.getField() + i++, campoError.getDefaultMessage());
        }

        detalles.put("errores",detalleCampos);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalles);
    }*/


}
