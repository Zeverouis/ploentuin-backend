package nl.ploentuin.ploentuin.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import nl.ploentuin.ploentuin.repository.UserRepository;
import nl.ploentuin.ploentuin.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class AdminOnStartup implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminOnStartup(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByRole(User.Role.ADMIN)) {
            User admin = new User();
            admin.setUsername("defaultadmin");
            admin.setPassword(passwordEncoder.encode("Admin123"));
            admin.setRole(User.Role.ADMIN);
            admin.setEmail("admin@superrealemailadress.com");
            userRepository.save(admin);

            System.out.println("Initial ADMIN user created: defaultadmin / Admin123");
        }
    }
}

