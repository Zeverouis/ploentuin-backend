package nl.ploentuin.ploentuin.dto.planner;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlannerListDto {
    private int id;
    private String title;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
