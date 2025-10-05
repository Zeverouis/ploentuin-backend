package nl.ploentuin.ploentuin.model;

import java.time.LocalDateTime;

public class Comment {

    private int id;

    private int userId;

    private int postId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String content;

    private String imageUrl;
}
