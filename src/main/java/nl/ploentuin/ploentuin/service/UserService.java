package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.user.*;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserInfoMinimalDto toMinimalDto(User u) {
        return new UserInfoMinimalDto(u.getId(), u.getUsername(),
                u.isEmailVerified(), u.getEmail(), u.getRole());
    }

    public UserInfoMinimalDto register(UserRegisterDto dto) {
        if (userRepository.existsByUsernameIgnoreCase(dto.getUsername()))
            throw new IllegalArgumentException("Gebruikersnaam is al in gebruik");

        if (userRepository.existsByEmailIgnoreCase(dto.getEmail()))
            throw new IllegalArgumentException("Emailadress is al in gebruik");

        User user = new User(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getEmail(),
                false,
                User.Role.USER
        );

        User savedUser = userRepository.save(user);

        return toMinimalDto(savedUser);
    }

    public Optional<UserInfoMinimalDto> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .map(this::toMinimalDto);
    }

    public Optional<UserInfoMinimalDto> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .map(this::toMinimalDto);
    }

    public List<UserInfoMinimalDto> findAllByRole(User.Role role) {
        return userRepository.findAllByRole(role)
                .stream()
                .map(this::toMinimalDto)
                .collect(Collectors.toList());
    }

    public UserInfoMinimalDto updateRole(int userId, UpdateUserRoleDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Geen gebruiker gevonden"));
        user.setRole(dto.getRole());

        return toMinimalDto(userRepository.save(user));
    }

    public UserInfoMinimalDto updateEmail(int userId, UpdateEmailDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Geen gebruiker gevonden"));

        String newEmail = dto.getEmail().trim();

        if (userRepository.existsByEmailIgnoreCase(newEmail) &&
                !user.getEmail().equalsIgnoreCase(newEmail)) {
            throw new IllegalArgumentException("Emailadress is al in gebruik");
        }
        user.setEmail(newEmail);
        user.setEmailVerified(false);

        return toMinimalDto(userRepository.save(user));
    }

    public UserInfoMinimalDto changePassword(int userId, ChangePasswordDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Geen gebruiker gevonden"));
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Oud wachtwoord is onjuist");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        return toMinimalDto(userRepository.save(user));
    }

    public List<UserInfoMinimalDto> getAllVerifiedUsers() {
        return userRepository.findAllByEmailVerified(true)
                .stream()
                .map(this::toMinimalDto)
                .collect(Collectors.toList());
    }
}
