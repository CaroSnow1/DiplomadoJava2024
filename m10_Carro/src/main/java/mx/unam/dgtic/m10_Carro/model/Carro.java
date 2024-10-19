package mx.unam.dgtic.m10_Carro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carro {
    private Integer id;
    private String modelo;
    private String color;
    private String matricula;
}
