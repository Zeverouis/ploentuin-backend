package nl.ploentuin.ploentuin.dto.planner;

import java.time.LocalDateTime;
import java.util.List;

public class UpdatePlannerDto {
    private String title;
    private int rows;
    private int columns;
    private List<PlannerItemDto> items;
}
