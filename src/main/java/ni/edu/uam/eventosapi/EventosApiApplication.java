package ni.edu.uam.eventosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Esta es la clase principal que enciende todo el motor de mi API de Eventos
@SpringBootApplication
public class EventosApiApplication {

    // El método main, el punto de partida obligatorio para que corra el proyecto
    public static void main(String[] args) {
        // Le digo a Spring Application que arranque mi API usando esta misma clase como configuración
        SpringApplication.run(EventosApiApplication.class, args);
    }

}