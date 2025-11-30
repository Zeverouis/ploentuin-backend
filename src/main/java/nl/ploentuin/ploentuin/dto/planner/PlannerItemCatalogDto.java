package nl.ploentuin.ploentuin.dto.planner;

import lombok.*;
import nl.ploentuin.ploentuin.model.PlannerItemCatalog;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlannerItemCatalogDto {
    private int id;
    private String name;
    private String colour;
    private String imageUrl;
    private PlannerItemCatalog.PlannerItemType type;
}
