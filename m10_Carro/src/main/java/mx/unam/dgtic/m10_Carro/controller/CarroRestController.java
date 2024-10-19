package mx.unam.dgtic.m10_Carro.controller;

import mx.unam.dgtic.m10_Carro.model.Carro;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/carro")
public class CarroRestController {

    public static final String NOMBRE = "Carolina Álvarez Rodea";
    public static final String NOMBRE1 = "Gustavo Villarreal Fajardo";
    public static final String NOMBRE2 = "Rodrigo Martínez Zambrano";
    public static final String NOMBRE3 = "Alejandro Noyola Nazario";

    HashMap<Integer, Carro> carros;

    public CarroRestController() {
        carros = new HashMap<>();
        carros.put(0, new Carro(0, "Mustang", "negro", "15HGM5"));
        carros.put(1, new Carro(1, "Chevrolet", "rojo","16SC45"));
        carros.put(2, new Carro(2, "Nisan", "gris", "12E4ED"));
        carros.put(3, new Carro(3, "Mazda", "azul", "19LOS5"));
    }

    @GetMapping(path = "/saludar", produces = MediaType.TEXT_HTML_VALUE)
    public String saludar() {
        return "<div style='text-align: center;'>" +
                "<h1 style='color: red;'>Integrantes:</h1><br>" +
                "<h2>" + NOMBRE + "</h2>" +
                "<h2>" + NOMBRE1 + "</h2>" +
                "<h2>" + NOMBRE2 + "</h2>" +
                "<h2>" + NOMBRE3 + "</h2>" +
                "</div>";
    }

    @GetMapping(path = "/", headers = {"Accept=application/json"},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, Carro>> getAll(){
        return new ResponseEntity<>(carros, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Carro> getCarro(@PathVariable int id){
        Carro carro = carros.get(id);
        if(carro != null){
            return ResponseEntity.ok(carro);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Carro> addCarro(@RequestBody Carro carro){
        int id = 1;
        while(carros.containsKey(id)){
            id++;
        }
        carro.setId(id);
        carros.put(id, carro);
        return new ResponseEntity<>(carro, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Carro> reemplazarCarro(@PathVariable int id, @RequestBody Carro carro){
        if(carros.containsKey(id)){
            carros.replace(id, carro);
            return ResponseEntity.ok(carros.get(id));
        }else{
            carros.put(id, carro);
            return new ResponseEntity<>(carros.get(id), HttpStatus.CREATED);
        }
    }

    @PutMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putNoPermitido(){
        return new ResponseEntity<>("{'msg':'Acción no permitida'}", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Carro> actualizarCarro(@PathVariable int id, @RequestBody Carro carro){
        Carro carroDB = carros.get(id);
        if(carro== null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        if(carroDB == null){
            return ResponseEntity.notFound().build();
        }
        if(carro.getModelo() != null){
            carroDB.setModelo(carro.getModelo());
        }
        if(carro.getColor() != null){
            carroDB.setColor(carro.getColor());
        }
        if(carro.getMatricula() != null){
            carroDB.setMatricula(carro.getMatricula());
        }

        carros.replace(id, carroDB);
        return ResponseEntity.ok(carros.get(id));
    }

    @PatchMapping("/")
    public ResponseEntity<String> patchNoPerminitido() {
        return new ResponseEntity<>("{'msg':'Accion no permitida'}", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Carro> deleteCarro(@PathVariable int id){
        if(carros.containsKey(id)){
            return ResponseEntity.ok(carros.remove(id));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
