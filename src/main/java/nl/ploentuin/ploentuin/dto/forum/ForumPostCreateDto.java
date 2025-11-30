package nl.ploentuin.ploentuin.dto.forum;

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
public class ForumPostCreateDto {

    @NotBlank (message = "Voeg een titel toe")
    private String title;

    @NotBlank (message = "Mag niet leeg zijn")
    private String content;
    private MultipartFile[] images;
    private String[] imageUrls;
}
