package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.user.*;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.UserRepository;
import nl.ploentuin.ploentuin.security.JwtUtil;
import nl.ploentuin.ploentuin.security.SecurityHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final SecurityHelper securityHelper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       EmailService emailService, JwtUtil jwtUtil, SecurityHelper securityHelper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
        this.securityHelper = securityHelper;
    }

    private UserInfoMinimalDto toMinimalDto(User u) {
        return new UserInfoMinimalDto(u.getId(), u.getUsername(),
                u.isEmailVerified(), u.getEmail(), u.getRole(), u.isBanned(), u.getAvatarUrl());
    }

    private UserInfoPublicDto toPublicDto(User u) {
        return new UserInfoPublicDto(u.getUsername(), u.getPlanners(), u.getRole(), u.getAvatarUrl());
    }

    public UserInfoMinimalDto register(UserRegisterDto dto) {
        if (userRepository.existsByUsernameIgnoreCase(dto.getUsername()))
            throw new IllegalArgumentException("Gebruikersnaam is al in gebruik");

        if (userRepository.existsByEmailIgnoreCase(dto.getEmail()))
            throw new IllegalArgumentException("Emailadress is al in gebruik");

        String verificationToken = UUID.randomUUID().toString();

        User user = new User(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getEmail(),
                false,
                User.Role.USER,
                false,
                null
        );

        user.setEmailVerificationToken(verificationToken);
        User savedUser = userRepository.save(user);

        emailService.sendVerificationEmail(
                savedUser.getEmail(),
                verificationToken
        );

        return toMinimalDto(savedUser);
    }

    public void verifyEmail(VerificationTokenDto token) {
        User user = userRepository.findByEmailVerificationToken(token.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Ongeldige of verlopen verificatietoken"));

        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        userRepository.save(user);
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

    public Optional<UserInfoPublicDto> findPublicUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .map(this::toPublicDto);
    }

    public UserInfoMinimalDto updateRole(String username, UpdateUserRoleDto dto) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("Geen gebruiker gevonden"));
        user.setRole(dto.getRole());

        return toMinimalDto(userRepository.save(user));
    }

    public UserInfoMinimalDto updateEmail(UpdateEmailDto dto) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsernameIgnoreCase(currentUsername)
                .orElseThrow(() -> new IllegalArgumentException("Geen gebruiker gevonden"));

        String newEmail = dto.getEmail().trim();

        if (userRepository.existsByEmailIgnoreCase(newEmail) &&
                !user.getEmail().equalsIgnoreCase(newEmail)) {
            throw new IllegalArgumentException("Emailadres is al in gebruik");
        }

        user.setEmail(newEmail);
        user.setEmailVerified(false);

        return toMinimalDto(userRepository.save(user));
    }

    public UserInfoMinimalDto changePassword(String username, ChangePasswordDto dto) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("Gebruiker niet gevonden"));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Oud wachtwoord is onjuist");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        return toMinimalDto(userRepository.save(user));
    }

    public UserInfoMinimalDto updateAvatar(String avatarUrl) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameIgnoreCase(currentUsername)
                .orElseThrow(() -> new IllegalArgumentException("Gebruiker niet gevonden"));

        user.setAvatarUrl(avatarUrl);
        return toMinimalDto(userRepository.save(user));
    }

    public List<UserInfoMinimalDto> getAllVerifiedUsers() {
        return userRepository.findAllByEmailVerified(true)
                .stream()
                .map(this::toMinimalDto)
                .collect(Collectors.toList());
    }

    public UserInfoMinimalDto toggleBan(String username) {
        if (securityHelper.isCurrentUsername(username)) {
            throw new IllegalArgumentException("Je kunt jezelf niet verbannen.");
        }

        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("User niet gevonden"));

        user.setBanned(!user.isBanned());
        return toMinimalDto(userRepository.save(user));
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
