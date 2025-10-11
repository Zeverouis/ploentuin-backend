package nl.ploentuin.ploentuin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "images")
public class Image extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int parentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParentType parentType;

    @Column(nullable = false)
    private String imageUrl;

    @Column(length = 500)
    private String caption;

    public enum ParentType{
        COMMENT,
        FORUMPOST,
        INFOPAGE
    }

    public Image(int parentId, ParentType parentType, String imageUrl) {
        this.parentId = parentId;
        this.parentType = parentType;
        this.imageUrl = imageUrl;
    }
}
