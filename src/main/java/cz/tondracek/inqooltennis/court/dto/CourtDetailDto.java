package cz.tondracek.inqooltennis.court.dto;

import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import lombok.Value;

import java.util.UUID;

@Value
public class CourtDetailDto {
    UUID id;
    String name;
    SurfaceTypeDetailDto surfaceType;
    boolean deleted;
}
