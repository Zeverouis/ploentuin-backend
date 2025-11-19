package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.user.UserRegisterDto;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(UserRegisterDto dto) {
        if (userRepository.existsByUsernameIgnoreCase(dto.getUsername()))
            throw new IllegalArgumentException("Gebruikersnaam is al in gebruik");

        if (userRepository.existsByEmailIgnoreCase(dto.getEmail()))
            throw new IllegalArgumentException("Emailadress is al in gebruik");

        User user = new User(
                user.setUsername(dto.getUsername());
        );
    }
}
