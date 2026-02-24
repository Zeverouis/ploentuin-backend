package nl.ploentuin.ploentuin.dto.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.ploentuin.ploentuin.dto.image.ImageResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponseDto {

    private int id;
    private int userId;
    private int forumPostId;
    private String username;
    private String content;
    private boolean userBanned;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String role;
    private String avatarUrl;

    private List<ImageResponseDto> images;
}
