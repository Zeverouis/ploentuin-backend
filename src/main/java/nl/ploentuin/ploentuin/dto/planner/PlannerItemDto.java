package nl.ploentuin.ploentuin.dto.planner;

import jakarta.validation.constraints.NotNull;
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
    private int id;
    private int row;
    private int column;
    private String colour;
    private String imageUrl;
    private String name;

    @NotNull(message = "Type is nodig, kies uit: BUSHES, FLOWERS, TREES, FRUIT_TREES, HERBS, CLIMBERS," +
            " GRASSES, FRUITS, VEGETABLES, AQUATICS, SUCCULENTS")
    private PlannerItem.PlannerItemType type;

    //TODO: Move this to the service layer later!
    public boolean hasVisual() {
        return (colour != null && !colour.isBlank()) || (imageUrl != null && !imageUrl.isBlank());
    }
}
