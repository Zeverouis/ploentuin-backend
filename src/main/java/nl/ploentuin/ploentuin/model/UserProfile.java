package nl.ploentuin.ploentuin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    private int id;

    private String avatarUrl = "https://img.icons8.com/?size=100&id=14736&format=png&color=000000";

    @Column(columnDefinition = "TEXT")
    private String about;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public UserProfile(String avatarUrl, String about) {
        if (avatarUrl != null && !avatarUrl.isBlank()) {
            this.avatarUrl = avatarUrl;
        }
        this.about = about;
    }
}
