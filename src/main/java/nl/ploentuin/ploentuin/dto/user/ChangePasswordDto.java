package nl.ploentuin.ploentuin.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    @NotBlank(message = "Voer je oude wachtwoord in")
    private String currentPassword;

    @NotBlank(message = "Voer je nieuwe wachtwoord in")
    private String newPassword;
}
