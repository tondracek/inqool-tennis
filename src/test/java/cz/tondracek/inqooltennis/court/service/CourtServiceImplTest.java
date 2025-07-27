package cz.tondracek.inqooltennis.court.service;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.court.data.CourtRepository;
import cz.tondracek.inqooltennis.court.dto.CourtDetailDto;
import cz.tondracek.inqooltennis.court.mapper.CourtMapper;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static cz.tondracek.inqooltennis.court.CourtSample.COURT;
import static cz.tondracek.inqooltennis.court.CourtSample.COURT_2;
import static cz.tondracek.inqooltennis.court.CourtSample.COURT_2_DTO;
import static cz.tondracek.inqooltennis.court.CourtSample.COURT_DELETED;
import static cz.tondracek.inqooltennis.court.CourtSample.COURT_DTO;
import static cz.tondracek.inqooltennis.court.CourtSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.court.CourtSample.UPDATED_COURT;
import static cz.tondracek.inqooltennis.court.CourtSample.UPDATED_COURT_DTO;
import static cz.tondracek.inqooltennis.court.CourtSample.UPDATE_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourtServiceImplTest {

    @Mock
    private CourtRepository repository;
    @Mock
    private SurfaceTypeRepository surfaceTypeRepository;

    @Mock
    private CourtMapper mapper;

    @InjectMocks
    private CourtServiceImpl service;

    @Test
    void createCourt() {
        when(mapper.toCourt(eq(CREATE_DTO), any(UUID.class), eq(COURT.getSurfaceType()), eq(false))).thenReturn(COURT);
        when(surfaceTypeRepository.findById(COURT.getSurfaceType().getId())).thenReturn(COURT.getSurfaceType());
        when(repository.create(COURT)).thenReturn(COURT);
        when(mapper.toDetailDto(COURT)).thenReturn(COURT_DTO);

        CourtDetailDto result = service.createCourt(CREATE_DTO);

        assertEquals(COURT_DTO, result);
    }

    @Test
    void updateCourt() {
        when(repository.findById(COURT.getId())).thenReturn(COURT);
        when(surfaceTypeRepository.findById(UPDATE_DTO.getSurfaceTypeId())).thenReturn(UPDATED_COURT.getSurfaceType());
        when(mapper.toCourt(UPDATE_DTO, COURT, UPDATED_COURT.getSurfaceType())).thenReturn(UPDATED_COURT);
        when(repository.update(UPDATED_COURT)).thenReturn(UPDATED_COURT);
        when(mapper.toDetailDto(UPDATED_COURT)).thenReturn(UPDATED_COURT_DTO);

        CourtDetailDto result = service.updateCourt(
                UPDATED_COURT.getId(),
                UPDATE_DTO
        );

        assertEquals(UPDATED_COURT_DTO, result);
    }


    @Test
    void updateCourt_failOnOriginalDeleted() {
        when(repository.findById(COURT_DELETED.getId())).thenReturn(COURT_DELETED);

        assertThrows(NotFoundException.class, () -> service.updateCourt(COURT_DELETED.getId(), UPDATE_DTO));
    }

    @Test
    void softDeleteCourt() {
        when(repository.findById(COURT.getId())).thenReturn(COURT);
        when(repository.update(COURT_DELETED)).thenReturn(COURT_DELETED);

        service.softDeleteCourt(COURT.getId());

        Mockito.verify(repository).update(COURT_DELETED);
    }

    @Test
    void getCourtById() {
        when(repository.findById(COURT.getId())).thenReturn(COURT);
        when(mapper.toDetailDto(COURT)).thenReturn(COURT_DTO);

        CourtDetailDto result = service.getCourtById(COURT.getId());

        assertEquals(COURT_DTO, result);
    }

    @Test
    void getAllCourts() {
        when(repository.findAllActive()).thenReturn(List.of(COURT, COURT_2));
        when(mapper.toDetailDto(COURT)).thenReturn(COURT_DTO);
        when(mapper.toDetailDto(COURT_2)).thenReturn(COURT_2_DTO);

        List<CourtDetailDto> result = service.getAllCourts();

        assertEquals(2, result.size());
        assertEquals(COURT_DTO, result.get(0));
        assertEquals(COURT_2_DTO, result.get(1));
    }
}