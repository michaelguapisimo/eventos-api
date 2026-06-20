package ni.edu.uam.eventosapi.repository;

import ni.edu.uam.eventosapi.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
}