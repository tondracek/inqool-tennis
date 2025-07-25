package cz.tondracek.inqooltennis.surfacetype.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.surfacetype.mapper.SurfaceTypeEntityMapper;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SurfaceTypeRepositoryImpl implements SurfaceTypeRepository {

    private final SurfaceTypeDao dao;

    private final SurfaceTypeEntityMapper mapper;

    public SurfaceTypeRepositoryImpl(
            SurfaceTypeDao dao,
            SurfaceTypeEntityMapper surfaceTypeEntityMapper
    ) {
        this.dao = dao;
        this.mapper = surfaceTypeEntityMapper;
    }

    @Override
    public SurfaceType create(SurfaceType surfaceType) {
        SurfaceTypeEntity entity = mapper.toEntity(surfaceType);

        dao.save(entity);

        return surfaceType;
    }

    @Override
    public SurfaceType update(SurfaceType surfaceType) {
        SurfaceTypeEntity entity = mapper.toEntity(surfaceType);

        dao.update(entity);

        return surfaceType;
    }

    @Override
    public void delete(UUID id) {
        SurfaceTypeEntity entity = dao.findById(id).orElseThrow(NotFoundException::new);
        dao.delete(entity);
    }

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
