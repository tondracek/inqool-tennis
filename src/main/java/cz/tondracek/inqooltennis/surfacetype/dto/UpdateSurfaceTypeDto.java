package cz.tondracek.inqooltennis.surfacetype.dto;

import cz.tondracek.inqooltennis.common.dto.PriceDto;
import lombok.Value;

@Value
public class UpdateSurfaceTypeDto {
    String name;
    PriceDto pricePerMinute;
}
