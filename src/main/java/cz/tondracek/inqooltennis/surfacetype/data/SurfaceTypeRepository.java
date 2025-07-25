package cz.tondracek.inqooltennis.surfacetype.data;

import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;

import java.util.List;
import java.util.UUID;

public interface SurfaceTypeRepository {

    SurfaceType create(SurfaceType surfaceType);

    SurfaceType update(SurfaceType surfaceType);

    void delete(UUID id);

    SurfaceType findById(UUID id);

    List<SurfaceType> findAllActive();
}
