package cz.tondracek.inqooltennis.surfacetype.dto;

import cz.tondracek.inqooltennis.common.price.dto.PriceDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class UpdateSurfaceTypeDto {
    @NotBlank
    String name;
    @Valid
    PriceDto pricePerMinute;
}
