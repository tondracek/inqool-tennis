package cz.tondracek.inqooltennis.surfacetype.mapper;

import cz.tondracek.inqooltennis.common.mapper.PriceMapper;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeEntity;
import cz.tondracek.inqooltennis.surfacetype.dto.CreateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.dto.UpdateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = PriceMapper.class)
public interface SurfaceTypeMapper {

    @Mapping(target = "withDeleted", ignore = true)
    SurfaceType toSurfaceType(CreateSurfaceTypeDto dto, UUID id, boolean deleted);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "pricePerMinute", source = "dto.pricePerMinute")
    @Mapping(target = "withDeleted", ignore = true)
    SurfaceType toSurfaceType(UpdateSurfaceTypeDto dto, SurfaceType original);

    SurfaceTypeDetailDto toDetailDto(SurfaceType surfaceType);

    SurfaceTypeEntity toEntity(SurfaceType model);

    @Mapping(target = "withDeleted", ignore = true)
    SurfaceType toModel(SurfaceTypeEntity entity);
}
