package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.user.*;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.UserRepository;
import nl.ploentuin.ploentuin.security.JwtUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       EmailService emailService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
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

    public String generateJwtForUser(User user) {
        return jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        ));
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Ongeldige of verlopen token"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);
    }

    public Optional<UserInfoMinimalDto> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .map(this::toMinimalDto);
    }

    public Optional<User> findUserEntityByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
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

    public Optional<UserInfoMinimalDto> getUserById(int userId) {
        return userRepository.findById(userId).map(this::toMinimalDto);
    }

    public void deleteUser(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User niet gevonden");
        }
        userRepository.deleteById(userId);
    }

    public void sendPasswordReset(String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Email niet gevonden"));

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);

        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
    }
}
