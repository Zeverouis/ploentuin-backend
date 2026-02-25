package nl.ploentuin.ploentuin.dto.planner;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlannerInfoDto {
    private int id;
    private String title;
    private int rows;
    private int columns;
    private int userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PlannerItemPlacementDto> items;
}
