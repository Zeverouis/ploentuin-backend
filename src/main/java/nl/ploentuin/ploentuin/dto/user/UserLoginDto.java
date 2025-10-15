package nl.ploentuin.ploentuin.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    @NotBlank(message = "Mag niet niks zijn")
    private String username;

    @NotBlank(message = "Mag niet niks zijn")
    private String password;
}
