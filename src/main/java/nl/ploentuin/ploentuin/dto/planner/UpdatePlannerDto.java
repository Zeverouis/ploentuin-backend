package nl.ploentuin.ploentuin.dto.planner;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePlannerDto {
    private String title;
    private int rows;
    private int columns;
    private List<PlannerItemPlacementDto> items;
}
