package nl.ploentuin.ploentuin.dto.planner;

import java.time.LocalDateTime;
import java.util.List;

public class PlannerInfoDto {
    private int id;
    private String title;
    private int rows;
    private int columns;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PlannerItemDto> items;
}
