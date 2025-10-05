package nl.ploentuin.ploentuin.model;

import java.time.LocalDateTime;

public class Image {

    private int id;

    private int parentId;

    private ParentType parentType;

    private String imageUrl;

    private String caption;

    private LocalDateTime uploadedAt;

    public enum ParentType{
        COMMENT,
        FORUMPOST,
        INFOPAGE
    }
}
