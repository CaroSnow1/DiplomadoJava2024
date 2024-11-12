package mx.unam.dgtic.servicio;

import mx.unam.dgtic.model.Alumno;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface IAlumnoService {
    List<Alumno> getAlumnosList();
    Optional<Alumno> getAlumnoById(String matricula);
    Alumno updateAlumno(Alumno alumno);
    Alumno createAlumno(Alumno alumno);
    boolean deleteAlumno(String matricula);
    List<Alumno> findAlumnosByEstados(String estado);
}
