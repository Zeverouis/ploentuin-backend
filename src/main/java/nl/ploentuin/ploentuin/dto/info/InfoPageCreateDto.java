package nl.ploentuin.ploentuin.dto.info;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InfoPageCreateDto {

    @NotBlank(message = "Voeg een titel toe")
    private String title;

    @NotBlank(message = "Voeg een TLDR toe")
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

    private int infoCategoryId;
}
