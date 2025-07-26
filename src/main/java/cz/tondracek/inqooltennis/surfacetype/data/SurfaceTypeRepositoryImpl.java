package cz.tondracek.inqooltennis.surfacetype.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.surfacetype.mapper.SurfaceTypeMapper;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SurfaceTypeRepositoryImpl implements SurfaceTypeRepository {

    private final SurfaceTypeDao dao;
    private final SurfaceTypeMapper mapper;

    public SurfaceTypeRepositoryImpl(
            SurfaceTypeDao dao,
            SurfaceTypeMapper surfaceTypeMapper
    ) {
        this.dao = dao;
        this.mapper = surfaceTypeMapper;
    }

    @NotNull
    @Override
    public SurfaceType create(SurfaceType surfaceType) {
        SurfaceTypeEntity entity = mapper.toEntity(surfaceType);

        dao.save(entity);
        return surfaceType;
    }

    @NotNull
    @Override
    public SurfaceType update(SurfaceType surfaceType) {
        SurfaceTypeEntity entity = mapper.toEntity(surfaceType);

        dao.update(entity);
        return surfaceType;
    }

    @NotNull
    @Override
    public SurfaceType findById(UUID id) {
        SurfaceTypeEntity entity = dao.findById(id).orElseThrow(NotFoundException::new);

        return mapper.toModel(entity);
    }

    @Override
    public List<SurfaceType> findAllActive() {
        return dao.findAllActive()
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
