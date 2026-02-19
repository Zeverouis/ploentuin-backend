package nl.ploentuin.ploentuin.dto.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.ploentuin.ploentuin.model.Image;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageResponseDto {

    private int id;
    private int parentId;
    private Image.ParentType parentType;
    private int userId;
    private String caption;
    private String imageUrl;
    private byte[] data;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
