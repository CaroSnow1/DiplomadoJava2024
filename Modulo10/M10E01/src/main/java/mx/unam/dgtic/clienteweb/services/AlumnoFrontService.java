package mx.unam.dgtic.clienteweb.services;

import mx.unam.dgtic.dto.AlumnoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AlumnoFrontService {
    public static final String API_URL = "http://127.0.0.1:8080/api/v2/alumnos";
    @Autowired
    private RestTemplate restTemplate;

    public AlumnoDto getAlumnoByMatricula(String matricula){
        String url = API_URL + "/"+matricula;
        return restTemplate.getForObject(url, AlumnoDto.class);
    }


}
