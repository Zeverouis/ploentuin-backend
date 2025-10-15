package nl.ploentuin.ploentuin.dto.planner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlannerDto {
    private String title;
    private int rows;
    private int columns;
}
