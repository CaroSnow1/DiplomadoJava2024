package mx.unam.dgtic.servicio;

import mx.unam.dgtic.dto.AlumnoDto;
import mx.unam.dgtic.exception.EstadoNoExisteException;
import mx.unam.dgtic.model.Alumno;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;


public interface IAlumnoDtoService {
    List<AlumnoDto> getAlumnosList();
    List<Alumno> getAlumnosPageableList(int page, int size, String dirSort, String sort);
    Optional<AlumnoDto> getAlumnoById(String matricula);
    AlumnoDto updateAlumno(AlumnoDto alumno) throws ParseException, EstadoNoExisteException;
    AlumnoDto createAlumno(AlumnoDto alumno) throws ParseException, EstadoNoExisteException;
    boolean deleteAlumno(String matricula);
    List<AlumnoDto> findAlumnosByEstados(String estado);
}

