package cz.tondracek.inqooltennis.surfacetype.dto;

import cz.tondracek.inqooltennis.common.price.dto.PriceDto;
import lombok.Value;

import java.util.UUID;

@Value
public class SurfaceTypeDetailDto {
    UUID id;
    String name;
    PriceDto pricePerMinute;
    boolean deleted;
}
