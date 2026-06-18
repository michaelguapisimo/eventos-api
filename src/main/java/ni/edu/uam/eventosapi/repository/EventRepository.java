package ni.edu.uam.eventosapi.repository;

import ni.edu.uam.eventosapi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}

