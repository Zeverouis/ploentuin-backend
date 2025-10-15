package nl.ploentuin.ploentuin.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BanUserDto {
    @NotNull(message = "Wie wil je bannen?")
    private Integer userId;


    @NotBlank(message = "Geef een reden op")
    @Getter
    private String reason;
}
