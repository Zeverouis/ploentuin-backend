package nl.ploentuin.ploentuin.dto.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoPageUpdateDto {

    private String title;
    private String tldr;

    private String sectionOneTitle;
    private String sectionOneContent;

    private String sectionTwoTitle;
    private String sectionTwoContent;

    private String sectionThreeTitle;
    private String sectionThreeContent;

    private String sectionFourTitle;
    private String sectionFourContent;

    private String[] captions;
    private MultipartFile[] images;
    private String[] imageUrls;
}
