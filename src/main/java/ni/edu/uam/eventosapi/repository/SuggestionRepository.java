package ni.edu.uam.eventosapi.repository;

import ni.edu.uam.eventosapi.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

// Esta interfaz se encarga de toda la comunicación con la tabla "suggestions" en Supabase.
// Al heredar de JpaRepository, tengo listos todos los métodos CRUD automáticos para crear,
// listar y borrar las propuestas que mandan los estudiantes antes de que las apruebe.
public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
    // Vinculado a la entidad "Suggestion" usando "Integer" para su ID autoincremental
}