package nl.ploentuin.ploentuin.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {
    @NotBlank(message = "Geen token gevonden")
    private String token;

    @NotBlank(message = "Voer een wachtwoord in")
    private String newPassword;
}
