package nl.ploentuin.ploentuin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
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

    @Column
    private String resetToken;

    @Column
    private String emailVerificationToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserProfile userProfile;

    public enum Role {
        USER,
        MOD,
        ADMIN
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Planner> planners = new ArrayList<>();

    public String getAvatarUrl() {
        return (userProfile != null) ? userProfile.getAvatarUrl() : "https://img.icons8.com/?size=100&id=14736&format=png&color=000000";
    }

    public String getAbout() {
        return (userProfile != null) ? userProfile.getAbout() : "";
    }

    public User(String username, String password, String email, boolean emailVerified, Role role, boolean banned, String avatarUrl, String about) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.emailVerified = emailVerified;
        this.role = role;
        this.banned = banned;
        UserProfile profile = new UserProfile();
        profile.setAvatarUrl(avatarUrl);
        profile.setAbout(about);
        profile.setUser(this);
        this.userProfile = profile;
    }
}