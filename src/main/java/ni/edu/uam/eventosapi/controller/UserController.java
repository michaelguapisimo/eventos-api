package ni.edu.uam.eventosapi.controller;

import ni.edu.uam.eventosapi.model.User;
import ni.edu.uam.eventosapi.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(null);

        if (user.getBlocked() == null) {
            user.setBlocked(false);
        }

        if (user.getUserType() == null || user.getUserType().isBlank()) {
            user.setUserType("ESTUDIANTE");
        }

        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

    @PatchMapping("/{id}/block")
    public User toggleBlockUser(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setBlocked(!Boolean.TRUE.equals(user.getBlocked()));

        return userRepository.save(user);
    }
}