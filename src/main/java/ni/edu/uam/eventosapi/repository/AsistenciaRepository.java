package ni.edu.uam.eventosapi.repository;

import ni.edu.uam.eventosapi.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {
    // Busca todos los registros de asistencia asociados a un evento específico
    List<Asistencia> findByEventoId(Integer eventoId);
}