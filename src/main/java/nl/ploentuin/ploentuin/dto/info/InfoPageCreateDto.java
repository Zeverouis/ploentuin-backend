package nl.ploentuin.ploentuin.dto.info;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InfoPageCreateDto {
    @NotBlank (message = "Voeg een titel toe")
    private String title;

    @NotBlank (message = "Er moet wel iets te lezen zijn")
    private String content;


    private int infoCategoryId;
}
