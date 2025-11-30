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
    private String content;
    private String[] captions;
    private MultipartFile[] images;
    private String[] imageUrls;
}
