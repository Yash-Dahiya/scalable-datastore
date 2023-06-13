package usersCrud.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public users createUser(@RequestBody users user) {
        return userRepository.createUser(user);
    }

    @PutMapping("/{id}")
    public users updateUser(@PathVariable int id, @RequestBody users user) {
        user.setId(id);
        return userRepository.updateUser(user);
    }

    @GetMapping("/{id}")
    public users getUser(@PathVariable int id) {
        return userRepository.getUser(id);
    }

    @GetMapping
    public List<users> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteUser(id);
    }
}

