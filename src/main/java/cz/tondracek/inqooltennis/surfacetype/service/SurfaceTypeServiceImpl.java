package cz.tondracek.inqooltennis.surfacetype.service;

import cz.tondracek.inqooltennis.surfacetype.dto.CreateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.dto.UpdateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SurfaceTypeServiceImpl implements SurfaceTypeService {
    private final Map<UUID, SurfaceType> surfaceTypes = new HashMap<>();

    @Override
    public SurfaceTypeDetailDto createSurfaceType(CreateSurfaceTypeDto dto) {
        return null;
    }

    @Override
    public SurfaceTypeDetailDto updateSurfaceType(UUID id, UpdateSurfaceTypeDto updateSurfaceTypeDto) {
        return null;
    }

    @Override
    public List<SurfaceTypeDetailDto> getAllSurfaceTypes() {
        return List.of();
    }

    @Override
    public boolean deleteSurfaceType(UUID id) {
        return false;
    }
}
