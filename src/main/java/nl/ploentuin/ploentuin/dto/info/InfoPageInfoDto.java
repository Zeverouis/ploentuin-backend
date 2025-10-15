package nl.ploentuin.ploentuin.dto.info;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoPageInfoDto {
    private int id;
    private String title;
    private String content;
    private InfoCategoryDto category;
}
