package nl.ploentuin.ploentuin.dto.user;

import lombok.Getter;
import nl.ploentuin.ploentuin.model.User.Role;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoMinimalDto {
    private int id;
    private String username;
    private boolean emailVerified;
    private String email;
    private Role role;
}
