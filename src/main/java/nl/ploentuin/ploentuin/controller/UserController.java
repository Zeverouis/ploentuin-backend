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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody VerificationTokenDto token) {
        try {
            userService.verifyEmail(token);
            return ResponseHelper.ok(null, "Email succesvol geverifieerd!");
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

    @PreAuthorize("@securityHelper.isCurrentUser()")
    @PatchMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestBody UpdateEmailDto dto) {
        try {
            UserInfoMinimalDto updated = userService.updateEmail(dto);
            return ResponseHelper.ok(updated, "Emailadres veranderd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("@securityHelper.isCurrentUsername(#username)")
    @PatchMapping("/{username}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable String username, @RequestBody @Valid ChangePasswordDto dto) {
        try {
            UserInfoMinimalDto updated = userService.changePassword(username, dto);
            return ResponseHelper.ok(updated, "Wachtwoord aangepast");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("@securityHelper.isCurrentUser()")
    @PatchMapping("/about")
    public ResponseEntity<?> updateAbout(@RequestBody UpdateAboutDto dto) {
        try {
            UserInfoMinimalDto updated = userService.updateAbout(dto.getAbout());
            return ResponseHelper.ok(updated, "Over mij bijgewerkt");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{username}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable String username, @RequestBody UpdateUserRoleDto dto) {
        try {
            UserInfoMinimalDto updated = userService.updateRole(username, dto);
            return ResponseHelper.ok(updated, "User rol aangepast");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{username}/ban")
    public ResponseEntity<ApiResponse<UserInfoMinimalDto>> banUser(@PathVariable String username) {
        try {
            UserInfoMinimalDto updated = userService.toggleBan(username);
            String message = updated.isBanned() ? "User is verbannen" : "User is weer welkom";
            return ResponseHelper.ok(updated, message);
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or @securityHelper.isCurrentUsername(#username)")
    @GetMapping("/user/{username}")
    public ResponseEntity<ApiResponse<UserInfoMinimalDto>> getUser(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(user -> ResponseHelper.ok(user, "User gevonden"))
                .orElseGet(() -> ResponseHelper.notFound("User niet gevonden"));
    }

    @GetMapping("/public/{username}")
    public ResponseEntity<ApiResponse<UserInfoPublicDto>> getPublicUser(@PathVariable String username) {
        return userService.findPublicUserByUsername(username)
                .map(user -> ResponseHelper.ok(user, "User gevonden"))
                .orElseGet(() -> ResponseHelper.notFound("User niet gevonden"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserInfoMinimalDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllVerifiedUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoMinimalDto>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseHelper.unauthorized("Niet ingelogd");
        }

        return userService.findByUsername(userDetails.getUsername())
                .map(user -> ResponseHelper.ok(user, "Sessie is nog geldig"))
                .orElseGet(() -> ResponseHelper.unauthorized("User niet gevonden"));
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<ApiResponse<UserInfoPublicDto>> getProfile(@PathVariable String username) {
        return userService.findPublicUserByUsername(username)
                .map(user -> ResponseHelper.ok(user, "Profiel van " + username + " geladen"))
                .orElseGet(() -> ResponseHelper.notFound("Profiel niet gevonden"));
    }

    @PreAuthorize("@securityHelper.isCurrentUser()")
    @PatchMapping("/avatar")
    public ResponseEntity<?> updateAvatar(@RequestBody UpdateAvatarDto dto) {
        try {
            UserInfoMinimalDto updated = userService.updateAvatar(dto.getAvatarUrl());
            return ResponseHelper.ok(updated, "Avatar bijgewerkt");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }
}