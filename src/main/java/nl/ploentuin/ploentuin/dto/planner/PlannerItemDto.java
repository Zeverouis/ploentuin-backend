package nl.ploentuin.ploentuin.dto.planner;

import nl.ploentuin.ploentuin.model.PlannerItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlannerItemDto {
    private int row;
    private int column;
    private String colour;
    private String imageUrl;
    private PlannerItem.PlannerItemType type;
}
