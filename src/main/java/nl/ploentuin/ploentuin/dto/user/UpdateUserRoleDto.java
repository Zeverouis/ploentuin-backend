package nl.ploentuin.ploentuin.dto.user;

import jakarta.validation.constraints.NotNull;
import nl.ploentuin.ploentuin.model.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRoleDto {
    @NotNull(message = "Voeg een rol toe")
    private User.Role role;
}
