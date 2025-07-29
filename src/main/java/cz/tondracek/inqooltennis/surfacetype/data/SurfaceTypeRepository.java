package cz.tondracek.inqooltennis.surfacetype.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;

import java.util.List;
import java.util.UUID;

public interface SurfaceTypeRepository {

    /**
     * @param surfaceType the surface type to be created
     * @return the created surface type
     */
    SurfaceType create(SurfaceType surfaceType);

    /**
     * @param surfaceType the surface type to be updated
     * @return the updated surface type
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    SurfaceType update(SurfaceType surfaceType);

    /**
     * ⚠️ Can return court with deleted=true
     *
     * @param id the UUID of the surface type to find
     * @return the surface type with the given ID, if it exists
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    SurfaceType findById(UUID id);

    /**
     * @return a list of all non-deleted surface types
     */
    List<SurfaceType> findAllActive();
}
