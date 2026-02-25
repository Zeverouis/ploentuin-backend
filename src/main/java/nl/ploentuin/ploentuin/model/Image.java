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

    @Column(nullable = false)
    private int userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParentType parentType;

    @Column
    private String imageUrl;

    @Column(length = 500)
    private String caption;

    @Lob
    @org.hibernate.annotations.JdbcType(org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType.class)
    @Column(name = "data")
    private byte[] data;

    public enum ParentType{
        COMMENT,
        FORUMPOST,
        INFOPAGE
    }

    public Image(int parentId, int userId, String caption, ParentType parentType, byte[] data) {
        this.parentId = parentId;
        this.userId = userId;
        this.caption = caption;
        this.parentType = parentType;
        this.data = data;
    }
}
