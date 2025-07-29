package cz.tondracek.inqooltennis.court.data;

import cz.tondracek.inqooltennis.court.model.Court;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public interface CourtRepository {

    @NotNull Court create(Court entity);

    @NotNull Court update(Court entity);

    /**
     * Can return court with deleted=true
     */
    @NotNull Court findById(UUID id);

    @NotNull Court findActiveById(UUID id);

    List<Court> findAllActive();
}
