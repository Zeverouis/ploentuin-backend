package nl.ploentuin.ploentuin.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmailDto {
    @NotBlank(message = "Mag niet niks zijn")
    @Email(message = "Is geen emailadress")
    private String email;
}
