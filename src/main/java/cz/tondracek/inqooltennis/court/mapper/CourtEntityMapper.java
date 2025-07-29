package cz.tondracek.inqooltennis.court.mapper;

import cz.tondracek.inqooltennis.court.data.CourtEntity;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.surfacetype.mapper.SurfaceTypeEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SurfaceTypeEntityMapper.class})
public interface CourtEntityMapper {

    @Mapping(target = "createdAt", ignore = true)
    CourtEntity toEntity(Court court);

    @Mapping(target = "withDeleted", ignore = true)
    Court toModel(CourtEntity entity);
}
