package mx.unam.dgtic.dto;

import jakarta.validation.constraints.*;

import java.util.Objects;

/**
 * Alumno DTO es un objeto para manejar datos hacia el front
 * @author  caroar
 * @version 0.0.1
 *
 */

public class AlumnoDto {
    @NotNull(message = "La matricula no debe ser nula")
    @NotBlank(message = "La matricula no debe ser un texto en blanco")
    private String matricula;

    @NotBlank(message = "El nombre del alumno no debe ser un texto en blanco")
    private String nombre;

    @NotBlank(message = "El apellido del alumno no debe ser un texto en blanco")
    private String paterno;

    //@Past(message = "La fecha debe ser en pasado")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",
            message = "El formato de la fecha de nacimiento es del tipo AAAA-MM-DD")
    @NotBlank(message = "La fecha de nacimiento no debe estar en blanco.")
    private String fnac;

    @Positive(message = "La estatura debe ser un valor numerico positivo")
    @DecimalMin(value = "0.5", message = "La estatura no debe ser menor de 0.5 m")
    @DecimalMax(value = "2.6", message = "La estatura no debe sobrepasar de 2.5 m")
    private double estatura;

    @NotNull(message = "Debes proporcionar un estado")
    @NotBlank(message = "Debes proporcionar un texto con el nombre del estado")
    private String estado;

    /**
     * Este constructor crea un alumno vacío
     */
    public AlumnoDto() {
    }

    /**
     * Este constructor recibe:
     *
     * @param matricula matricula del tipo string con formato '1A @type String
     * @param nombre descripcion
     * @param paterno descripcion
     * @param fnac descripcion
     * @param estatura descripcion
     * @param estado descripcion
     */
    public AlumnoDto(String matricula, String nombre, String paterno, String fnac, double estatura, String estado) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.paterno = paterno;
        this.fnac = fnac;
        this.estatura = estatura;
        this.estado = estado;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Este metodo retorna el apellido paterno del alumno
     * @return @type String retorna un String con el apellido
     */
    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getFnac() {
        return fnac;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlumnoDto alumnoDto)) return false;
        return Objects.equals(matricula, alumnoDto.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(matricula);
    }

    @Override
    public String toString() {
        return "AlumnoDto{" +
                "matricula='" + matricula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", paterno='" + paterno + '\'' +
                ", fnac='" + fnac + '\'' +
                ", estatura=" + estatura +
                ", estado='" + estado + '\'' +
                '}';
    }
}
