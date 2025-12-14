package nl.ploentuin.ploentuin.controller;

import jakarta.validation.Valid;
import nl.ploentuin.ploentuin.dto.user.*;
import nl.ploentuin.ploentuin.dto.api.ApiResponse;
import nl.ploentuin.ploentuin.dto.api.ResponseHelper;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterDto dto) {
        try {
            UserInfoMinimalDto response = userService.register(dto);
            return ResponseHelper.ok(response, "User geregistreerd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Valid UserLoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );

            User user = userService.findUserEntityByUsername(loginDto.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String jwt = userService.generateJwtForUser(user);

            return ResponseHelper.ok(jwt, "Login succesvol!");
        } catch (Exception e) {
            return ResponseHelper.badRequest("Ongeldige gebruikersnaam of wachtwoord");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            userService.sendPasswordReset(email);
            return ResponseHelper.ok(null, "Password reset token verstuurd!");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordDto dto) {
        try {
            userService.resetPassword(dto.getToken(), dto.getNewPassword());
            return ResponseHelper.ok(null, "Wachtwoord succesvol aangepast!");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("@securityHelper.isCurrentUser(#id)")
    @PatchMapping("/{id}/email")
    public ResponseEntity<?> changeEmail(@PathVariable int id, @RequestBody UpdateEmailDto dto) {
        try {
            UserInfoMinimalDto updated = userService.updateEmail(id, dto);
            return ResponseHelper.ok(updated, "Email veranderd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("@securityHelper.isCurrentUser(#id)")
    @PatchMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable int id, @RequestBody @Valid ChangePasswordDto dto) {
        try {
            UserInfoMinimalDto updated = userService.changePassword(id, dto);
            return ResponseHelper.ok(updated, "Wachtwoord aangepast");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable int id, @RequestBody UpdateUserRoleDto dto) {
        try {
            UserInfoMinimalDto updated = userService.updateRole(id, dto);
            return ResponseHelper.ok(updated, "User rol aangepast");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseHelper.ok(null, "User verwijderd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or @securityHelper.isCurrentUser(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserInfoMinimalDto>> getUser(@PathVariable int id) {
        return userService.getUserById(id)
                .map(user -> ResponseHelper.ok(user, "User gevonden"))
                .orElseGet(() -> ResponseHelper.notFound("User niet gevonden"));
    }

    @GetMapping("/public/{username}")
    public ResponseEntity<ApiResponse<UserInfoMinimalDto>> getPublicUser(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(user -> ResponseHelper.ok(user, "User gevonden"))
                .orElseGet(() -> ResponseHelper.notFound("User niet gevonden"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserInfoMinimalDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllVerifiedUsers());
    }
}