package ni.edu.uam.eventosapi.repository;

import ni.edu.uam.eventosapi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

// Esta interfaz es mi conexión directa con la tabla "events" en Supabase.
// Al heredar de JpaRepository, Spring ya me regala todos los métodos básicos
// de un CRUD (findAll, findById, save, delete, etc.) sin que yo tenga que escribir código SQL.
public interface EventRepository extends JpaRepository<Event, Integer> {
    // Manejo la entidad "Event" y su llave primaria es de tipo "Integer"
}