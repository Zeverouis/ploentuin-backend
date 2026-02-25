package nl.ploentuin.ploentuin.dto.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InfoPageFilterDto {
    private int id;
    private String categoryName;
    private List<InfoPageMinimalDto> pages;
}
