package nl.ploentuin.ploentuin.dto;

import nl.ploentuin.ploentuin.model.User.Role;

public class UserInfoMinimalDto {
    private int id;
    private String username;
    private boolean emailVerified;
    private Role role;
}
