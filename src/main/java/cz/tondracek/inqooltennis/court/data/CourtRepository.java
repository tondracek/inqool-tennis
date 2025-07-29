package cz.tondracek.inqooltennis.court.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.court.model.Court;

import java.util.List;
import java.util.UUID;

public interface CourtRepository {

    /**
     * @param court the court to be created
     * @return the created court
     */
    Court create(Court court);

    /**
     * @param court the court to be updated
     * @return the updated court
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    Court update(Court court);

    /**
     * ⚠️ Can return court with deleted=true
     *
     * @param id the UUID of the court to find
     * @return the court with the given ID, if it exists
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    Court findById(UUID id);

    /**
     * @param id the UUID of the court to find
     * @return the non-deleted court with the given ID, if it exists
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    Court findActiveById(UUID id);

    /**
     * @return a list of all non-deleted courts
     */
    List<Court> findAllActive();
}
