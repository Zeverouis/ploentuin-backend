package nl.ploentuin.ploentuin.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.ploentuin.ploentuin.model.Planner;
import nl.ploentuin.ploentuin.model.User.Role;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfoPublicDto {
    private String username;
    private List<Planner> planners;
    private Role role;
    private String avatarUrl;
}
