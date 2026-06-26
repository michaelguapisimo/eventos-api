package ni.edu.uam.eventosapi.model;

import jakarta.persistence.*;

// Mapeo esta clase como una entidad de JPA para gestionar las propuestas que envían los usuarios
@Entity
@Table(name = "suggestions") // La tabla correspondiente en Supabase se llamará "suggestions"
public class Suggestion {

    // Identificador único autoincremental para controlar cada sugerencia de forma independiente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title; // Título tentativo de la propuesta

    // Aquí le doy un margen más amplio al campo de descripción (1000 caracteres)
    // para que el usuario pueda detallar bien su idea sin quedarse corto
    @Column(length = 1000)
    private String description;

    private String date;        // Fecha sugerida para el evento
    private String location;    // Lugar propuesto para el evento
    private Integer maxCapacity; // Estimación de la capacidad máxima de personas

    // El constructor vacío que Hibernate necesita sí o sí para procesar la entidad
    public Suggestion() {
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}