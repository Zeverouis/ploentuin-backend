package nl.ploentuin.ploentuin.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "planner_item_placement")
public class PlannerItemPlacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id", nullable = false)
    private Planner planner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_item_id", nullable = false)
    private PlannerItemCatalog catalogItem;

    @Column(name = "row_num")
    private int row;

    @Column(name = "col_num")
    private int column;
}