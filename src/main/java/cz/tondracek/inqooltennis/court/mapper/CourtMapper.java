package cz.tondracek.inqooltennis.court.mapper;

import cz.tondracek.inqooltennis.court.dto.CourtDetailDto;
import cz.tondracek.inqooltennis.court.dto.CreateCourtDto;
import cz.tondracek.inqooltennis.court.dto.UpdateCourtDto;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.surfacetype.mapper.SurfaceTypeMapper;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {SurfaceTypeMapper.class})
public interface CourtMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "create.name")
    @Mapping(target = "surfaceType", source = "surfaceType")
    @Mapping(target = "withDeleted", ignore = true)
    Court toCourt(CreateCourtDto create, UUID id, SurfaceType surfaceType, boolean deleted);

    @Mapping(target = "id", source = "original.id")
    @Mapping(target = "name", source = "update.name")
    @Mapping(target = "surfaceType", source = "surfaceType")
    @Mapping(target = "deleted", source = "original.deleted")
    @Mapping(target = "withDeleted", ignore = true)
    Court toCourt(UpdateCourtDto update, Court original, SurfaceType surfaceType);

    CourtDetailDto toDetailDto(Court court);
}
