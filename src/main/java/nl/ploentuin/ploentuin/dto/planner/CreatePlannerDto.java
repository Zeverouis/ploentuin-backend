package nl.ploentuin.ploentuin.dto.planner;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlannerDto {
    @NotBlank(message = "Voeg een titel toe")
    private String title;

    private int rows;
    private int columns;
}
