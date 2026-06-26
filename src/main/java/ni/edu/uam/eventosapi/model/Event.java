package ni.edu.uam.eventosapi.model;

import jakarta.persistence.*;

// Mapeo esta clase como una entidad de JPA para que se transforme en una tabla real
@Entity
@Table(name = "events") // Le indico a Hibernate que la tabla en Supabase se llamará "events"
public class Event {

    // Identificador único de mi evento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Dejo que la base de datos maneje el autoincremento serial
    private Integer id;

    // Atributos principales del evento que llena el creador o administrador
    private String title;       // Título del evento
    private String description; // De qué trata el evento
    private String date;        // Fecha (manejada como string para facilitar el formato rápido)
    private String location;    // Nombre del lugar físico (ej: "Auditorio UAM")
    private Integer maxCapacity; // Límite de personas que caben en el evento

    // Control operativo interno del evento
    private Integer attendees;  // Contador de personas que ya le dieron "join"
    private Double latitude;    // Coordenadas para mostrar la ubicación en el mapa del frontend
    private Double longitude;
    private Boolean open;       // Bandera para saber si el evento sigue activo o ya cerró inscripciones
    private Boolean featured;   // Para destacar eventos especiales en la vista principal de la app

    // Constructor vacío obligatorio requerido por JPA/Hibernate para poder instanciar la entidad
    public Event() {
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

    public Integer getAttendees() {
        return attendees;
    }

    public void setAttendees(Integer attendees) {
        this.attendees = attendees;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }
}