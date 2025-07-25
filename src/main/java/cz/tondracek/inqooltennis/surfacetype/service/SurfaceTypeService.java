package cz.tondracek.inqooltennis.surfacetype.service;

import cz.tondracek.inqooltennis.surfacetype.dto.CreateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.dto.UpdateSurfaceTypeDto;

import java.util.List;
import java.util.UUID;

/**
 * A Service for managing Surface types of courts.
 * <p>
 * <b>Managing methods are only for ADMIN USERS</b>
 */
public interface SurfaceTypeService {

    SurfaceTypeDetailDto createSurfaceType(CreateSurfaceTypeDto createSurfaceTypeDto);

    SurfaceTypeDetailDto updateSurfaceType(UUID id, UpdateSurfaceTypeDto updateSurfaceTypeDto);

    List<SurfaceTypeDetailDto> getAllSurfaceTypes();

    void softDeleteSurfaceType(UUID id);
}
