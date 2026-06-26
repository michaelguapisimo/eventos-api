package ni.edu.uam.eventosapi.repository;

import ni.edu.uam.eventosapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Esta interfaz maneja todo el acceso de datos para la tabla "users" en Supabase
public interface UserRepository extends JpaRepository<User, Integer> {

    // Método personalizado: Spring genera la consulta SQL automática para revisar si un CIF ya existe.
    // Lo uso en el controlador como candado para evitar que dos usuarios se registren con el mismo carnet.
    boolean existsByCif(String cif);

    // Método personalizado: Comprueba si un nombre de usuario ya está ocupado en la base de datos.
    // Me sirve para asegurar que los nombres/alias sean únicos al momento de crear la cuenta.
    boolean existsByName(String name);
}