package nl.ploentuin.ploentuin.dto.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.ploentuin.ploentuin.dto.image.ImageResponseDto;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InfoPageInfoDto {
    private int id;
    private String title;
    private String content;
    private InfoCategoryDto category;
    private List<ImageResponseDto> images;
}
