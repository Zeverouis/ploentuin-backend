package nl.ploentuin.ploentuin.dto.info;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoCategoryDto {
        private Integer id;

        @NotBlank (message = "Mag niet niks zijn")
        private String categoryName;
}
