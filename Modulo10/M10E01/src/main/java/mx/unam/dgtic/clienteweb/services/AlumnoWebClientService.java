package mx.unam.dgtic.clienteweb.services;

import mx.unam.dgtic.dto.AlumnoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AlumnoWebClientService {
    @Autowired
    private WebClient webClient;

    public List<AlumnoDto> getAll(){
        Mono<List<AlumnoDto>> alumnosMono = webClient.get()
                .uri("/")
                .retrieve()
                .bodyToFlux(AlumnoDto.class)
                .collectList();
        List<AlumnoDto> alumnos = alumnosMono.block();
        return alumnos;
    }

    public AlumnoDto getAlumnoByMatricula(String matricula){
        Mono<AlumnoDto> alumnoDtoMono = webClient.get()
                .uri("/{matricula}", matricula)
                .retrieve()
                .bodyToMono(AlumnoDto.class);
        AlumnoDto alumnoDto = alumnoDtoMono.block();
        return alumnoDto;
    }

    public AlumnoDto actualizarAlumno(AlumnoDto alumnoDto){
        return webClient.put()
                .uri("/{matricula}", alumnoDto.getMatricula())
                .bodyValue(alumnoDto)
                .retrieve()
                .bodyToMono(AlumnoDto.class)
                .block();

    }


}
