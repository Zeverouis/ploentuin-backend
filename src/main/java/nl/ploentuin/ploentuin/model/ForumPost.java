package nl.ploentuin.ploentuin.model;

import java.time.LocalDateTime;

public class ForumPost {

    private int id;

    private int userId;

    private int categoryId;

    private String title;

    private String content;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;
}
