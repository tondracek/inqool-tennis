package cz.tondracek.inqooltennis.court.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourtDao {

    void save(CourtEntity entity);

    void update(CourtEntity entity);

    Optional<CourtEntity> findById(UUID id);

    List<CourtEntity> findAllActive();
}
