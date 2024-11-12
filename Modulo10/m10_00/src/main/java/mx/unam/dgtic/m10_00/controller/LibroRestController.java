package mx.unam.dgtic.m10_00.controller;

import mx.unam.dgtic.m10_00.model.Libro;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/libreria")
public class LibroRestController {
    public static final String NOMBRE = "Carolina Álvarez Rodea";

    HashMap<Integer, Libro> libreria;

    public LibroRestController() {
        libreria = new HashMap<>();
        libreria.put(0, new Libro(0, "Modulo 10", "Jesus Hernández"));
        libreria.put(1, new Libro(1, "El perfume", "Patrick Suskind"));
        libreria.put(2, new Libro(2, "El señor de los anillos", "J.R. Tolkien"));
        libreria.put(3, new Libro(3, "Fundación", "Issac Asimov"));
    }

    @GetMapping(path = "/saludar", produces = MediaType.TEXT_HTML_VALUE)
    public String saludar() {
        return "Hola " + NOMBRE;
    }

    //@GetMapping("/")
    @GetMapping(path="/", headers = {"Accept=application/json"},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, Libro>> getAll() {
        //return libreria;
        return new ResponseEntity<>(libreria, HttpStatus.OK);
    }

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Libro> getLibro(@PathVariable int id) {
        //return libreria.get(id);
        Libro libro = libreria.get(id);
        if(libro != null){
            return ResponseEntity.ok(libro); //200
        }else{
            return ResponseEntity.notFound().build(); //404
        }
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Libro> addBook(@RequestBody Libro libro) {
        int id = 1;
        while (libreria.containsKey(id)) {
            id++;
        }

        libro.setId(id);
        libreria.put(id, libro);
        //return libro;
        return new ResponseEntity<>(libro, HttpStatus.CREATED);
    }

    //Reemplazar un recurso
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Libro> reemplazarLibro(@PathVariable int id, @RequestBody Libro libro) {
        //libreria.replace(id, libro);
        if (libreria.containsKey(id)) {
            libreria.replace(id, libro);
            return ResponseEntity.ok(libreria.get(id));
        } else {
            libreria.put(id, libro);
            return new ResponseEntity<>(libreria.get(id), HttpStatus.CREATED);
        }
        //return libreria.get(id);
    }

    @PutMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String erroPut() {
        return "'mensaje': 'Error, este metodo (PUT) no soporta acciones a recurso completo'";
    }

    //Actualizacion parcial
    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Libro> actualizaLibro(@PathVariable int id, @RequestBody Libro libro) {
        Libro libroDB = libreria.get(id);

        if(libro == null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        if(libroDB == null){
            return ResponseEntity.notFound().build();
        }
        if (libro.getTitulo() != null) {
            libroDB.setTitulo(libro.getTitulo());
        }
        if (libro.getAutor() != null) {
            libroDB.setAutor(libro.getAutor());
        }

        libreria.replace(id, libroDB);
        //return libreria.get(id);
        return ResponseEntity.ok(libreria.get(id));
    }

    @PatchMapping("/")
    public ResponseEntity<String> patchNoPermitido(){
        return new ResponseEntity<>("{'msg': 'Accion no permitida'}", HttpStatus.METHOD_NOT_ALLOWED);
    }

    //Eliminar por id
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Libro> deleteLibro(@PathVariable int id){
        //return libreria.remove(id);
        if(libreria.containsKey(id)){
            return ResponseEntity.ok(libreria.remove(id));
        }else {
            return ResponseEntity.notFound().build();
        }
    }




}
