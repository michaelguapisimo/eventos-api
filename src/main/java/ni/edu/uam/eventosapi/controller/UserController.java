package ni.edu.uam.eventosapi.controller;

import ni.edu.uam.eventosapi.model.User;
import ni.edu.uam.eventosapi.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para gestionar los usuarios (Estudiantes y Administradores) de la app
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // CORS libre para que no me rebote el frontend en desarrollo
public class UserController {

    private final UserRepository userRepository;

    // Constructor para la inyección de dependencias del repositorio de usuarios
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Endpoint para listar todos los usuarios registrados
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Endpoint para registrar un usuario nuevo con validaciones de rol automáticas
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // Garantizo que el ID vaya nulo para que actúe como una inserción nueva en la BD
        user.setId(null);

        // 1. Candados para evitar datos duplicados esenciales
        if (userRepository.existsByCif(user.getCif())) {
            return ResponseEntity.badRequest().body("El CIF ya se encuentra registrado por otro usuario.");
        }

        if (userRepository.existsByName(user.getName())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya está en uso.");
        }

        // Si no mandan el estado de bloqueo, por defecto inicia activo (false)
        if (user.getBlocked() == null) {
            user.setBlocked(false);
        }

        // ============================================================
        // FILTRO DE ADMINISTRADORES POR CIF
        // ============================================================
        // Aquí mapeo nuestros CIF reales de la UAM para darnos permisos de Admin en automático
        String cifTuya = "23021825";
        String cifMichael = "24010555";
        String cifJulio = "23021490";
        String cifEnoc = "-24010536";

        // Si el CIF que se está registrando coincide con alguno de nuestro grupo, le doy rango de ADMIN
        if (user.getCif().equals(cifTuya) ||
                user.getCif().equals(cifMichael) ||
                user.getCif().equals(cifJulio) ||
                user.getCif().equals(cifEnoc)) {

            user.setUserType("ADMINISTRADOR");
        } else {
            // Cualquier otro CIF externo entra directamente como ESTUDIANTE sin privilegios
            user.setUserType("ESTUDIANTE");
        }
        // ============================================================

        // Guardo el usuario final ya clasificado en Supabase
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // Endpoint para eliminar un usuario del sistema por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        // Busco primero si existe para evitar errores raros de Hibernate
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para bloquear o desbloquear a un usuario (Baneo temporal)
    @PutMapping("/{id}/toggle-block")
    public ResponseEntity<User> toggleBlockUser(@PathVariable Integer id) {
        // Me aseguro de que el usuario exista
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Switch lógico: si estaba bloqueado (true), lo desbloqueo (false) y viceversa.
        // Valido el nulo primero por si acaso la BD guardó un vacío.
        boolean currentStatus = user.getBlocked() != null && user.getBlocked();
        user.setBlocked(!currentStatus);

        // Guardo el nuevo estado de la cuenta
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }
}