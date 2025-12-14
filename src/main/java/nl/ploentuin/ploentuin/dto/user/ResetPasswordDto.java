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
public class ResetPasswordDto {
    @NotBlank(message = "Geen token gevonden")
    private String token;

    @NotBlank(message = "Voer een wachtwoord in")
    private String newPassword;
}
