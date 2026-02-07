package nl.ploentuin.ploentuin.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "planner_item_catalog")
public class PlannerItemCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String colour;
    private String imageUrl;

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
        SUCCULENTS,
        GROUNDS
    }
}