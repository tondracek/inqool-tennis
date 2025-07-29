package cz.tondracek.inqooltennis.surfacetype.service;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeRepository;
import cz.tondracek.inqooltennis.surfacetype.dto.CreateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.dto.UpdateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.mapper.SurfaceTypeMapper;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SurfaceTypeServiceImpl implements SurfaceTypeService {

    private final SurfaceTypeRepository repository;

    private final SurfaceTypeMapper mapper;

    public SurfaceTypeServiceImpl(
            SurfaceTypeRepository repository,
            SurfaceTypeMapper surfaceTypeMapper
    ) {
        this.repository = repository;
        this.mapper = surfaceTypeMapper;
    }

    @Transactional
    @Override
    public SurfaceTypeDetailDto createSurfaceType(CreateSurfaceTypeDto dto) {
        UUID id = UUID.randomUUID();

        SurfaceType surfaceType = mapper.toSurfaceType(dto, id, false);
        SurfaceType result = repository.create(surfaceType);
        return mapper.toDetailDto(result);
    }

    @Transactional
    @Override
    public SurfaceTypeDetailDto updateSurfaceType(UUID id, UpdateSurfaceTypeDto dto) {
        SurfaceType original = repository.findById(id);
        if (original.isDeleted()) throw new NotFoundException();

        SurfaceType updated = mapper.toSurfaceType(dto, original);
        SurfaceType result = repository.update(updated);

        return mapper.toDetailDto(result);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SurfaceTypeDetailDto> getAllSurfaceTypes() {
        return repository.findAllActive()
                .stream()
                .map(mapper::toDetailDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public void softDeleteSurfaceType(UUID id) {
        SurfaceType original = repository.findById(id);

        SurfaceType updated = original.withDeleted(true);

        repository.update(updated);
    }
}
