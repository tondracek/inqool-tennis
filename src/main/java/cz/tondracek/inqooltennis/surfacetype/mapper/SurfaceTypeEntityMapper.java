package cz.tondracek.inqooltennis.surfacetype.mapper;

import cz.tondracek.inqooltennis.common.mapper.PriceEntityMapper;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeEntity;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PriceEntityMapper.class})
public interface SurfaceTypeEntityMapper {

    SurfaceTypeEntity toEntity(SurfaceType model);

    SurfaceType toModel(SurfaceTypeEntity entity);
}
