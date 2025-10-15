package nl.ploentuin.ploentuin.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    @NotBlank(message = "Mag niet niks zijn")
    private String username;

    @NotBlank(message = "Mag niet niks zijn")
    private String password;

    @NotBlank(message = "Heb je je email vergeten?")
    @Email(message = "Is geen emailadress jonguh!")
    private String email;
}
