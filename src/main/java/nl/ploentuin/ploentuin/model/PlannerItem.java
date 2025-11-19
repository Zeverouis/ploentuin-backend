package nl.ploentuin.ploentuin.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "planner_item")
public class PlannerItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id", nullable = false)
    private Planner planner;

    @Column(name = "planner_row")
    private int row;

    @Column(name = "planner_column")
    private int column;

    private String colour;

    private String imageUrl;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlannerItemType type;

    public enum PlannerItemType {
        BUSHES,
        FLOWERS,
        TREES,
        FRUIT_TREES,
        HERBS,
        CLIMBERS,
        GRASSES,
        FRUITS,
        VEGETABLES,
        AQUATICS,
        SUCCULENTS
    }
}
