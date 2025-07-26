package cz.tondracek.inqooltennis.surfacetype.data;

import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public interface SurfaceTypeRepository {

    @NotNull SurfaceType create(SurfaceType surfaceType);

    @NotNull SurfaceType update(SurfaceType surfaceType);

    @NotNull SurfaceType findById(UUID id);

    List<SurfaceType> findAllActive();
}
