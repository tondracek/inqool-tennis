package cz.tondracek.inqooltennis.court.mapper;

import cz.tondracek.inqooltennis.court.data.CourtEntity;
import cz.tondracek.inqooltennis.court.model.Court;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourtEntityMapper {
    CourtEntity toEntity(Court court);

    @Mapping(target = "withDeleted", ignore = true)
    Court toModel(CourtEntity entity);
}
