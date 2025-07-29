package cz.tondracek.inqooltennis.court.service;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.court.data.CourtRepository;
import cz.tondracek.inqooltennis.court.dto.CourtDetailDto;
import cz.tondracek.inqooltennis.court.dto.CreateCourtDto;
import cz.tondracek.inqooltennis.court.dto.UpdateCourtDto;
import cz.tondracek.inqooltennis.court.mapper.CourtMapper;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeRepository;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CourtServiceImpl implements CourtService {

    private final CourtRepository repository;
    private final SurfaceTypeRepository surfaceTypeRepository;

    private final CourtMapper mapper;

    public CourtServiceImpl(
            CourtRepository repository,
            SurfaceTypeRepository surfaceTypeRepository,
            CourtMapper courtMapper
    ) {
        this.repository = repository;
        this.surfaceTypeRepository = surfaceTypeRepository;
        this.mapper = courtMapper;
    }

    @Transactional
    @Override
    public CourtDetailDto createCourt(CreateCourtDto dto) {
        UUID id = UUID.randomUUID();

        SurfaceType surfaceType = surfaceTypeRepository.findById(dto.getSurfaceTypeId());
        Court court = mapper.toCourt(dto, id, surfaceType, false);

        Court result = repository.create(court);
        return mapper.toDetailDto(result);
    }

    @Transactional
    @Override
    public CourtDetailDto updateCourt(UUID id, UpdateCourtDto dto) {
        Court original = repository.findById(id);
        if (original.isDeleted()) throw new NotFoundException();

        SurfaceType surfaceType = surfaceTypeRepository.findById(dto.getSurfaceTypeId());
        Court updated = mapper.toCourt(dto, original, surfaceType);

        Court result = repository.update(updated);
        return mapper.toDetailDto(result);
    }

    @Transactional
    @Override
    public void softDeleteCourt(UUID id) {
        Court original = repository.findById(id);
        Court updated = original.withDeleted(true);

        repository.update(updated);
    }

    @Transactional(readOnly = true)
    @Override
    public CourtDetailDto getCourtById(UUID id) {
        Court court = repository.findById(id);
        return mapper.toDetailDto(court);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CourtDetailDto> getAllCourts() {
        return repository.findAllActive()
                .stream()
                .map(mapper::toDetailDto)
                .toList();
    }
}
