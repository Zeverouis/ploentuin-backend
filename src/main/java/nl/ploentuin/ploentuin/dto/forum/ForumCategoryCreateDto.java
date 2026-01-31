package nl.ploentuin.ploentuin.dto.forum;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForumCategoryCreateDto {
    @NotBlank
    private String categoryName;

}