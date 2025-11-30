package nl.ploentuin.ploentuin.model;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "planner")
public class Planner extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "anonymous_token", unique = true)
    private String anonymousToken;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int rows;

    @Column(nullable = false)
    private int columns;

    //TODO: possibly rename for DB rows and columns


    public Planner(User user, String title, int rows, int columns) {
        this.user = user;
        this.title = title;
        this.rows = rows;
        this.columns = columns;
    }

    public Planner(String title, int rows, int columns, String anonymousToken) {
        this.title = title;
        this.rows = rows;
        this.columns = columns;
    }
}
