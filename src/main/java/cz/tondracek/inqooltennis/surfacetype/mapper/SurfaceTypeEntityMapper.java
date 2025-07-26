package cz.tondracek.inqooltennis.surfacetype.mapper;

import cz.tondracek.inqooltennis.common.price.mapper.PriceEmbeddableMapper;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeEntity;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PriceEmbeddableMapper.class})
public interface SurfaceTypeEntityMapper {

    SurfaceTypeEntity toEntity(SurfaceType model);

    @Mapping(target = "withDeleted", ignore = true)
    SurfaceType toModel(SurfaceTypeEntity entity);
}
