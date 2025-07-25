package cz.tondracek.inqooltennis.surfacetype.mapper;

import cz.tondracek.inqooltennis.surfacetype.dto.CreateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.dto.UpdateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SurfaceTypeMapper {

    SurfaceType toSurfaceType(CreateSurfaceTypeDto dto, UUID id, boolean deleted);

    SurfaceType toSurfaceType(UpdateSurfaceTypeDto dto, SurfaceType original);

    SurfaceTypeDetailDto toDetailDto(SurfaceType surfaceType);
}
