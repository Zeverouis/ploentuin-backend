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
public class ForumPostResponseDto {

    private int id;
    private int userId;
    private int forumCategoryId;
    private String username;
    private String forumCategoryName;
    private String title;
    private String content;
    private boolean userBanned;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String role;
    private String avatarUrl;

    private List<CommentResponseDto> comments;
    private List<ImageResponseDto> images;
}
