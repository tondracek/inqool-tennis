package cz.tondracek.inqooltennis.surfacetype.dto;

import cz.tondracek.inqooltennis.common.dto.PriceDto;
import lombok.Data;

@Data
public class UpdateSurfaceTypeDto {
    private String name;
    private PriceDto pricePerMinute;
}
