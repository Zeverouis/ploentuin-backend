package nl.ploentuin.ploentuin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ploentuin_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(nullable = false, unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    private boolean emailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean banned = false;

    private String avatarUrl = "https://img.icons8.com/?size=100&id=14736&format=png&color=000000";

    @Column(name = "about", columnDefinition = "TEXT")
    private String about;

    @Column
    private String resetToken;

    @Column
    private String emailVerificationToken;

    public enum Role {
        USER,
        MOD,
        ADMIN
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Planner> planners = new ArrayList<>();

    public String getAvatarUrl() {
        if (this.avatarUrl == null || this.avatarUrl.isBlank()) {
            return "https://img.icons8.com/?size=100&id=14736&format=png&color=000000";
        }
        return this.avatarUrl;
    }

    public User(String username, String password, String email, boolean emailVerified, Role role, boolean banned, String avatarUrl, String about) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.emailVerified = emailVerified;
        this.role = role;
        this.banned = banned;

        if (avatarUrl != null && !avatarUrl.isBlank()) {
            this.avatarUrl = avatarUrl;
        }
        this.about = about;
    }

}
