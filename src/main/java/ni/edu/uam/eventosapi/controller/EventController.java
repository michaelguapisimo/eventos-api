package ni.edu.uam.eventosapi.controller;

import ni.edu.uam.eventosapi.model.Event;
import ni.edu.uam.eventosapi.repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Defino que esta clase es mi controlador REST para manejar las peticiones HTTP
@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*") // Permito peticiones desde cualquier origen para no tener problemas de CORS en el frontend
public class EventController {

    // Inyecto el repositorio para poder interactuar con la base de datos de Supabase
    private final EventRepository eventRepository;

    // Constructor para que Spring inyecte la dependencia automáticamente
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Endpoint para traer la lista completa de eventos
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Endpoint para buscar un solo evento por su ID. Si no existe, lanzo un error
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
    }

    // Endpoint para registrar un nuevo evento
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        // Me aseguro de que el ID vaya nulo para que la base de datos lo autoincremente y no intente sobrescribir algo
        event.setId(null);

        // Si por alguna razón no mandan los asistentes, los inicializo en 0 por seguridad
        if (event.getAttendees() == null) {
            event.setAttendees(0);
        }

        return eventRepository.save(event);
    }

    // Endpoint especial para que un usuario se una a un evento
    @PostMapping("/{id}/join")
    public ResponseEntity<Event> joinEvent(@PathVariable Integer id) {
        // 1. Busco el evento en Supabase por su ID para asegurarme de que exista
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        // 2. Verifico si ya se alcanzó la capacidad máxima antes de dejar entrar a alguien
        if (event.getAttendees() >= event.getMaxCapacity()) {
            return ResponseEntity.badRequest().body(event);
        }

        // 3. Incremento en +1 el número de asistentes reales
        event.setAttendees(event.getAttendees() + 1);

        // 4. Guardo el cambio permanentemente en Supabase
        Event updatedEvent = eventRepository.save(event);

        return ResponseEntity.ok(updatedEvent);
    }

    // Endpoint para actualizar toda la información de un evento existente
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Integer id, @RequestBody Event eventDetails) {
        // Primero verifico que el evento realmente exista antes de intentar editarlo
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con id: " + id));

        // Sincronizo todos los campos editados usando los datos que me mandaron en el cuerpo de la petición
        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setDate(eventDetails.getDate());
        event.setLocation(eventDetails.getLocation());
        event.setMaxCapacity(eventDetails.getMaxCapacity());
        event.setAttendees(eventDetails.getAttendees());
        event.setLatitude(eventDetails.getLatitude());
        event.setLongitude(eventDetails.getLongitude());
        event.setOpen(eventDetails.getOpen());
        event.setFeatured(eventDetails.getFeatured());

        // Guardo los cambios en la base de datos
        Event updatedEvent = eventRepository.save(event);
        return ResponseEntity.ok(updatedEvent);
    }

    // Endpoint para eliminar un evento por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        // Verifico que exista el evento antes de borrarlo
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con id: " + id));

        // Lo elimino de Supabase y devuelvo un estado 204 (No Content) indicando que todo salió bien
        eventRepository.delete(event);
        return ResponseEntity.noContent().build();
    }
}