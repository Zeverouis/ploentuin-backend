package nl.ploentuin.ploentuin.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "planner")
public class Planner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private int rows;

    @Column(nullable = false)
    private int columns;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Planner(User user, String title, int rows, int columns) {
        this.user = user;
        this.title = title;
        this.rows = rows;
        this.columns = columns;
    }

    public Planner(String title, int rows, int columns) {
        this.title = title;
        this.rows = rows;
        this.columns = columns;
    }
}
