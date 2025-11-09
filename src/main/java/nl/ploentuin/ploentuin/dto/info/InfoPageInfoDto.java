package nl.ploentuin.ploentuin.dto.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InfoPageInfoDto {
    private int id;
    private String title;
    private String content;
    private InfoCategoryDto category;
}
