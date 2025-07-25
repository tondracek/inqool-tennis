package cz.tondracek.inqooltennis.surfacetype.dto;

import cz.tondracek.inqooltennis.common.dto.PriceDto;
import lombok.Data;

import java.util.UUID;

@Data
public class SurfaceTypeDetailDto {
    private UUID id;
    private String name;
    private PriceDto price;
    private boolean deleted;
}
