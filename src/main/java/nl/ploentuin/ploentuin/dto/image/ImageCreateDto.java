package nl.ploentuin.ploentuin.dto.image;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageCreateDto {

    private MultipartFile[] images;
    private String[] urls;
    private String[] captions;
}
