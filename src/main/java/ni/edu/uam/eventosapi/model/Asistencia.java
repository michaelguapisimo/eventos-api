package ni.edu.uam.eventosapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "evento_id", nullable = false)
    private Integer eventoId;

    @Column(name = "estudiante_id", nullable = false)
    private String estudianteId;

    @Column(name = "nombre_estudiante", nullable = false)
    private String nombreEstudiante;

    @Column(nullable = false)
    private Boolean llego = false;

    // Constructores
    public Asistencia() {}

    public Asistencia(Integer eventoId, String estudianteId, String nombreEstudiante, Boolean llego) {
        this.eventoId = eventoId;
        this.estudianteId = estudianteId;
        this.nombreEstudiante = nombreEstudiante;
        this.llego = llego;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getEventoId() { return eventoId; }
    public void setEventoId(Integer eventoId) { this.eventoId = eventoId; }

    public String getEstudianteId() { return estudianteId; }
    public void setEstudianteId(String estudianteId) { this.estudianteId = estudianteId; }

    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }

    public Boolean getLlego() { return llego; }
    public void setLlego(Boolean llego) { this.llego = llego; }
}