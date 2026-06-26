package ni.edu.uam.eventosapi.model;

import jakarta.persistence.*;

// Mapeo esta clase como una entidad JPA para gestionar las cuentas de la aplicación
@Entity
@Table(name = "users") // Nombre exacto de la tabla en Supabase
public class User {

    // Identificador único autoincremental para cada usuario registrado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // Nombre completo o alias elegido por el usuario

    private String cif;  // Código de identificación único de la UAM (Clave para las validaciones)

    private String userType; // Rol asignado en el controlador: "ADMINISTRADOR" o "ESTUDIANTE"

    // Bandera para saber si el usuario está baneado/bloqueado. Por defecto inicia en activo (false)
    private Boolean blocked = false;

    private String registrationDate; // Fecha en la que el usuario se registró en el sistema

    // Constructor vacío por defecto que Hibernate requiere obligatoriamente para procesar la entidad
    public User() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}