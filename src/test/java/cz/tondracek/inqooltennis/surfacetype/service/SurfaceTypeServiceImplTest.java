package cz.tondracek.inqooltennis.surfacetype.service;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeRepository;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.mapper.SurfaceTypeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_2;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_2_DTO;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_DELETED;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_DTO;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.UPDATED_SURFACE_TYPE;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.UPDATED_SURFACE_TYPE_DTO;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.UPDATE_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SurfaceTypeServiceImplTest {

    @Mock
    private SurfaceTypeRepository repository;

    @Mock
    private SurfaceTypeMapper mapper;

    @InjectMocks
    private SurfaceTypeServiceImpl service;

    @Test
    void createSurfaceType_shouldReturnCorrectly() {
        when(mapper.toSurfaceType(eq(CREATE_DTO), any(UUID.class), eq(false))).thenReturn(SURFACE_TYPE);
        when(repository.create(eq(SURFACE_TYPE))).thenReturn(SURFACE_TYPE);
        when(mapper.toDetailDto(eq(SURFACE_TYPE))).thenReturn(SURFACE_TYPE_DTO);

        SurfaceTypeDetailDto result = service.createSurfaceType(CREATE_DTO);

        assertEquals(SURFACE_TYPE_DTO, result);
    }

    @Test
    void updateSurfaceType_shouldReturnCorrectly() {
        when(repository.findById(eq(SURFACE_TYPE.getId()))).thenReturn(SURFACE_TYPE);
        when(mapper.toSurfaceType(eq(UPDATE_DTO), eq(SURFACE_TYPE))).thenReturn(UPDATED_SURFACE_TYPE);
        when(repository.update(eq(UPDATED_SURFACE_TYPE))).thenReturn(UPDATED_SURFACE_TYPE);
        when(mapper.toDetailDto(eq(UPDATED_SURFACE_TYPE))).thenReturn(UPDATED_SURFACE_TYPE_DTO);

        SurfaceTypeDetailDto result = service.updateSurfaceType(SURFACE_TYPE.getId(), UPDATE_DTO);

        assertEquals(UPDATED_SURFACE_TYPE_DTO, result);
    }

    @Test
    void updateSurfaceType_shouldFailOnOriginalDeleted() {
        when(repository.findById(eq(SURFACE_TYPE_DELETED.getId()))).thenReturn(SURFACE_TYPE_DELETED);

        assertThrows(NotFoundException.class, () -> service.updateSurfaceType(SURFACE_TYPE_DELETED.getId(), UPDATE_DTO));
    }

    @Test
    void getAllSurfaceTypes_shouldReturnCorrectly() {
        when(repository.findAllActive())
                .thenReturn(List.of(SURFACE_TYPE, SURFACE_TYPE_2));
        when(mapper.toDetailDto(SURFACE_TYPE)).thenReturn(SURFACE_TYPE_DTO);
        when(mapper.toDetailDto(SURFACE_TYPE_2)).thenReturn(SURFACE_TYPE_2_DTO);

        List<SurfaceTypeDetailDto> result = service.getAllSurfaceTypes();

        assertEquals(2, result.size());
        assertEquals(SURFACE_TYPE_DTO, result.get(0));
        assertEquals(SURFACE_TYPE_2_DTO, result.get(1));
    }

    @Test
    void softDeleteSurfaceType_shouldUpdateCorrectly() {
        when(repository.findById(eq(SURFACE_TYPE.getId()))).thenReturn(SURFACE_TYPE);
        when(repository.update(eq(SURFACE_TYPE_DELETED))).thenReturn(SURFACE_TYPE_DELETED);

        service.softDeleteSurfaceType(SURFACE_TYPE.getId());

        Mockito.verify(repository).update(SURFACE_TYPE_DELETED);
    }
}