package nl.ploentuin.ploentuin.controller;

import nl.ploentuin.ploentuin.dto.api.ApiResponse;
import nl.ploentuin.ploentuin.dto.api.ResponseHelper;
import nl.ploentuin.ploentuin.dto.user.UserInfoMinimalDto;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.existsByUsernameIgnoreCase(user.getUsername())) {
            return ResponseHelper.badRequest("Gebruikersnaam bestaat al");
        }
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            return ResponseHelper.badRequest("Emailadress bestaat al");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.USER);
        userRepository.save(user);

        return ResponseHelper.ok(null,"User geregistreerd");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PreAuthorize("hasRole('ADMIN') or @securityHelper.isCurrentUser(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable int id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseHelper.ok(user, "User gevonden");
        } else {
            return ResponseHelper.notFound("User niet gevonden");
        }
    }

    @GetMapping("/public/{username}")
    public ResponseEntity<ApiResponse<UserInfoMinimalDto>> getPublicUser(@PathVariable String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .map(user -> {
                    UserInfoMinimalDto dto = new UserInfoMinimalDto(
                            user.getId(),
                            user.getUsername(),
                            user.isEmailVerified(),
                            null,
                            user.getRole()
                    );
                    return ResponseHelper.ok(dto, "User gevonden!");
                })
                .orElseGet(() -> ResponseHelper.notFound("User niet gevonden"));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable int id, @RequestParam User.Role role) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseHelper.notFound("User niet gevonden");
        }

        User user = optionalUser.get();
        user.setRole(role);
        userRepository.save(user);
        return ResponseHelper.ok(null,"User rol aangepast " + role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable int id) {
        if (!userRepository.existsById(id)) {
            return ResponseHelper.notFound("User niet gevonden");
        }

        userRepository.deleteById(id);
        return ResponseHelper.ok(null, "User verwijderd");
    }
}