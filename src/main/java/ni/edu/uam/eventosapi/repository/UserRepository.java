package ni.edu.uam.eventosapi.repository;

import ni.edu.uam.eventosapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}