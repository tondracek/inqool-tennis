package cz.tondracek.inqooltennis.surfacetype.service;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.surfacetype.dto.CreateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.dto.UpdateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.mapper.SurfaceTypeMapper;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SurfaceTypeServiceImpl implements SurfaceTypeService {

    private final Map<UUID, SurfaceType> surfaceTypes = new HashMap<>();
    private final SurfaceTypeMapper surfaceTypeMapper;

    public SurfaceTypeServiceImpl(SurfaceTypeMapper surfaceTypeMapper) {
        this.surfaceTypeMapper = surfaceTypeMapper;
    }

    @Override
    public SurfaceTypeDetailDto createSurfaceType(CreateSurfaceTypeDto dto) {
        UUID id = UUID.randomUUID();
        SurfaceType surfaceType = surfaceTypeMapper.toSurfaceType(dto, id, false);

        surfaceTypes.put(id, surfaceType);

        return surfaceTypeMapper.toDetailDto(surfaceType);
    }

    @Override
    public SurfaceTypeDetailDto updateSurfaceType(UUID id, UpdateSurfaceTypeDto dto) {
        SurfaceType original = surfaceTypes.get(id);
        if (original == null || original.isDeleted())
            throw new NotFoundException();
        SurfaceType updated = surfaceTypeMapper.toSurfaceType(dto, original);

        surfaceTypes.put(id, updated);

        return surfaceTypeMapper.toDetailDto(updated);
    }

    @Override
    public List<SurfaceTypeDetailDto> getAllSurfaceTypes() {
        return surfaceTypes.values().stream()
                .map(surfaceTypeMapper::toDetailDto)
                .filter(surfaceTypeDetailDto -> !surfaceTypeDetailDto.isDeleted())
                .toList();
    }

    @Override
    public boolean deleteSurfaceType(UUID id) {
        SurfaceType original = surfaceTypes.get(id);
        if (original == null)
            throw new NotFoundException();

        SurfaceType updated = new SurfaceType(
                original.getId(),
                original.getName(),
                original.getPricePerMinute(),
                true
        );
        surfaceTypes.put(id, updated);

        return true;
    }
}
