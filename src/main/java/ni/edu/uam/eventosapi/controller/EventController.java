package ni.edu.uam.eventosapi.controller;

import ni.edu.uam.eventosapi.model.Event;
import ni.edu.uam.eventosapi.repository.EventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        event.setId(null);

        if (event.getAttendees() == null) {
            event.setAttendees(0);
        }

        return eventRepository.save(event);
    }
}