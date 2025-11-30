package nl.ploentuin.ploentuin.dto.planner;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlannerItemPlacementDto {
    private int id;
    private int row;
    private int column;
    private PlannerItemCatalogDto catalogItem;
}