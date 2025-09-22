package nl.ploentuin.ploentuin.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(nullable = false, unique = true)
    private String username;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    private boolean emailVerified;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public void setPassword(String password) {
        this.password = password;
    }

    public enum Role {
        USER,
        MOD,
        ADMIN
    }

    public User(String username, String password, String email, boolean emailVerified, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.emailVerified = emailVerified;
        this.role = role;
    }

}
