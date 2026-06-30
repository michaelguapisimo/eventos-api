package ni.edu.uam.eventosapi.controller;

import ni.edu.uam.eventosapi.model.Asistencia;
import ni.edu.uam.eventosapi.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencia")
@CrossOrigin(origins = "*") // Permite que tu teléfono o emulador se conecte sin bloqueos de CORS
public class AsistenciaController {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    // 1. Obtener la lista de alumnos de un evento específico (GET /api/asistencia/evento/5)
    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<Asistencia>> getAsistenciaPorEvento(@PathVariable Integer eventoId) {
        List<Asistencia> lista = asistenciaRepository.findByEventoId(eventoId);
        return ResponseEntity.ok(lista);
    }

    // 2. Insertar una nueva asistencia (Cuando un alumno se inscribe)
    @PostMapping("/inscribir")
    public ResponseEntity<Asistencia> registrarInscripcion(@RequestBody Asistencia asistencia) {
        Asistencia nuevaAsistencia = asistenciaRepository.save(asistencia);
        return ResponseEntity.ok(nuevaAsistencia);
    }

    // 3. Modificar si llegó o no llegó desde el cuadro de verificación (PUT /api/asistencia/check/1?llego=true)
    @PutMapping("/check/{id}")
    public ResponseEntity<Asistencia> actualizarCheckAsistencia(
            @PathVariable Integer id,
            @RequestParam Boolean llego
    ) {
        Asistencia asistencia = asistenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de asistencia no encontrado con id: " + id));

        asistencia.setLlego(llego);
        Asistencia actualizada = asistenciaRepository.save(asistencia);
        return ResponseEntity.ok(actualizada);
    }
}