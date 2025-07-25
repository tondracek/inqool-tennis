package cz.tondracek.inqooltennis.surfacetype.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SurfaceTypeDao {

    void save(SurfaceTypeEntity entity);

    Optional<SurfaceTypeEntity> findById(UUID id);

    List<SurfaceTypeEntity> findAll();

    void update(SurfaceTypeEntity entity);

    void delete(SurfaceTypeEntity entity);
}
