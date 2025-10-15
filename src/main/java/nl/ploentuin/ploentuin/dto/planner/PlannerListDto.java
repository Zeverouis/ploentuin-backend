package nl.ploentuin.ploentuin.dto.planner;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlannerListDto {
    private int id;
    private String title;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
