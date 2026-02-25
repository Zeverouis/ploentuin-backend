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
@Table(name = "info_page")
public class InfoPage extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private InfoCategory infoCategory;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String tldr;

    @Column(columnDefinition = "TEXT")
    private String sectionOneTitle;

    @Column(columnDefinition = "TEXT")
    private String sectionOneContent;

    @Column(columnDefinition = "TEXT")
    private String sectionTwoTitle;

    @Column(columnDefinition = "TEXT")
    private String sectionTwoContent;

    @Column(columnDefinition = "TEXT")
    private String sectionThreeTitle;

    @Column(columnDefinition = "TEXT")
    private String sectionThreeContent;

    @Column(columnDefinition = "TEXT")
    private String sectionFourTitle;

    @Column(columnDefinition = "TEXT")
    private String sectionFourContent;
}