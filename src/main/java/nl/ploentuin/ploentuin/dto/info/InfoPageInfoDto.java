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
    private String tldr;
    private InfoCategoryDto category;

    private String sectionOneTitle;
    private String sectionOneContent;
    private String sectionTwoTitle;
    private String sectionTwoContent;
    private String sectionThreeTitle;
    private String sectionThreeContent;
    private String sectionFourTitle;
    private String sectionFourContent;

    private List<ImageResponseDto> images;
}
