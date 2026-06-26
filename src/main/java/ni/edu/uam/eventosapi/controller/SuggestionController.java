package ni.edu.uam.eventosapi.controller;

import ni.edu.uam.eventosapi.model.Event;
import ni.edu.uam.eventosapi.model.Suggestion;
import ni.edu.uam.eventosapi.repository.EventRepository;
import ni.edu.uam.eventosapi.repository.SuggestionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Este controlador maneja las sugerencias de eventos que proponen los usuarios
@RestController
@RequestMapping("/api/suggestions")
@CrossOrigin(origins = "*") // CORS abierto para conectar fácil con mi frontend
public class SuggestionController {

    // Necesito ambos repositorios porque una sugerencia aprobada se convierte en un evento real
    private final SuggestionRepository suggestionRepository;
    private final EventRepository eventRepository;

    // Constructor para inyectar ambos repositorios mediante Spring
    public SuggestionController(
            SuggestionRepository suggestionRepository,
            EventRepository eventRepository
    ) {
        this.suggestionRepository = suggestionRepository;
        this.eventRepository = eventRepository;
    }

    // Endpoint para ver todas las sugerencias acumuladas
    @GetMapping
    public List<Suggestion> getAllSuggestions() {
        return suggestionRepository.findAll();
    }

    // Endpoint para que un usuario envíe una nueva sugerencia de evento
    @PostMapping
    public Suggestion createSuggestion(@RequestBody Suggestion suggestion) {
        // Fuerzo el ID a nulo para que la base de datos autoincremente y cree un registro limpio
        suggestion.setId(null);
        return suggestionRepository.save(suggestion);
    }

    // Endpoint crítico: Aprueba una sugerencia, la vuelve evento y borra la propuesta original
    @Transactional // Uso esto para asegurar que si algo falla, no me quede la base de datos a medias (Atomicidad)
    @PostMapping("/{id}/approve")
    public Event approveSuggestion(@PathVariable Integer id) {

        // 1. Busco la sugerencia para verificar que exista antes de hacer nada
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Sugerencia no encontrada")
                );

        // 2. Instancio un nuevo evento vacío para empezar a pasarle los datos aprobados
        Event event = new Event();

        // Copio la información que propuso el usuario desde la sugerencia
        event.setTitle(suggestion.getTitle());
        event.setDescription(suggestion.getDescription());
        event.setDate(suggestion.getDate());
        event.setLocation(suggestion.getLocation());
        event.setMaxCapacity(suggestion.getMaxCapacity());

        // Inicializo por defecto los campos técnicos/operativos que el usuario no controla en la sugerencia
        event.setAttendees(0); // Inicia con 0 asistentes reales
        event.setLatitude(0.0); // Coordenadas temporales por defecto
        event.setLongitude(0.0);
        event.setOpen(true); // El evento nace abierto al público
        event.setFeatured(false); // No nace destacado por defecto

        // 3. Guardo el nuevo evento ya oficializado en Supabase
        Event createdEvent = eventRepository.save(event);

        // 4. Como ya es un evento real, borro la sugerencia original de la lista de propuestas
        suggestionRepository.delete(suggestion);

        // Devuelvo el evento creado para confirmar en el frontend
        return createdEvent;
    }

    // Endpoint para rechazar o eliminar una sugerencia sin pasarla a eventos
    @DeleteMapping("/{id}")
    public void rejectOrDeleteSuggestion(@PathVariable Integer id) {
        suggestionRepository.deleteById(id);
    }
}