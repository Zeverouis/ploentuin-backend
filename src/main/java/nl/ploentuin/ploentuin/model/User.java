package nl.ploentuin.ploentuin.model;

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

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    private boolean emailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    public enum Role {
        USER,
        MOD,
        ADMIN
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Planner> planners = new ArrayList<>();

    public User(String username, String password, String email, boolean emailVerified, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.emailVerified = emailVerified;
        this.role = role;
    }

}
