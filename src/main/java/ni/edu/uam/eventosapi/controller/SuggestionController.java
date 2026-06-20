package ni.edu.uam.eventosapi.controller;

import ni.edu.uam.eventosapi.model.Event;
import ni.edu.uam.eventosapi.model.Suggestion;
import ni.edu.uam.eventosapi.repository.EventRepository;
import ni.edu.uam.eventosapi.repository.SuggestionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
@CrossOrigin(origins = "*")
public class SuggestionController {

    private final SuggestionRepository suggestionRepository;
    private final EventRepository eventRepository;

    public SuggestionController(
            SuggestionRepository suggestionRepository,
            EventRepository eventRepository
    ) {
        this.suggestionRepository = suggestionRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<Suggestion> getAllSuggestions() {
        return suggestionRepository.findAll();
    }

    @PostMapping
    public Suggestion createSuggestion(@RequestBody Suggestion suggestion) {
        suggestion.setId(null);
        return suggestionRepository.save(suggestion);
    }

    @Transactional
    @PostMapping("/{id}/approve")
    public Event approveSuggestion(@PathVariable Integer id) {

        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Sugerencia no encontrada")
                );

        Event event = new Event();

        event.setTitle(suggestion.getTitle());
        event.setDescription(suggestion.getDescription());
        event.setDate(suggestion.getDate());
        event.setLocation(suggestion.getLocation());
        event.setMaxCapacity(suggestion.getMaxCapacity());

        event.setAttendees(0);
        event.setLatitude(0.0);
        event.setLongitude(0.0);
        event.setOpen(true);
        event.setFeatured(false);

        Event createdEvent = eventRepository.save(event);

        suggestionRepository.delete(suggestion);

        return createdEvent;
    }

    @DeleteMapping("/{id}")
    public void rejectOrDeleteSuggestion(@PathVariable Integer id) {
        suggestionRepository.deleteById(id);
    }
}