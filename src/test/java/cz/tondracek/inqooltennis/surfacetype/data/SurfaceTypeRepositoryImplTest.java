package cz.tondracek.inqooltennis.surfacetype.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.surfacetype.mapper.SurfaceTypeEntityMapper;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SurfaceTypeRepositoryImplTest {

    @Mock
    private SurfaceTypeDao dao;
    @Mock
    private SurfaceTypeEntityMapper mapper;

    @InjectMocks
    private SurfaceTypeRepositoryImpl repository;

    @Test
    void create() {
        when(mapper.toEntity(SURFACE_TYPE)).thenReturn(SURFACE_TYPE_ENTITY);

        SurfaceType result = repository.create(SURFACE_TYPE);

        assertEquals(SURFACE_TYPE, result);
    }

    @Test
    void update() {
        when(mapper.toEntity(SURFACE_TYPE)).thenReturn(SURFACE_TYPE_ENTITY);

        SurfaceType result = repository.update(SURFACE_TYPE);

        assertEquals(SURFACE_TYPE, result);
    }

    @Test
    void findById_shouldReturnCorrectly() {
        when(dao.findById(SURFACE_TYPE_ENTITY.getId())).thenReturn(Optional.of(SURFACE_TYPE_ENTITY));
        when(mapper.toModel(SURFACE_TYPE_ENTITY)).thenReturn(SURFACE_TYPE);

        SurfaceType result = repository.findById(SURFACE_TYPE_ENTITY.getId());
        assertEquals(SURFACE_TYPE, result);
    }

    @Test
    void findById_shouldFailOnNotFound() {
        when(dao.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> repository.findById(SURFACE_TYPE_ENTITY.getId()));
    }

    @Test
    void findAllActive() {
        List<SurfaceTypeEntity> entities = List.of(SURFACE_TYPE_ENTITY, SURFACE_TYPE_ENTITY, SURFACE_TYPE_ENTITY);

        when(dao.findAllActive()).thenReturn(entities);
        when(mapper.toModel(SURFACE_TYPE_ENTITY)).thenReturn(SURFACE_TYPE);

        var result = repository.findAllActive();

        assertEquals(3, result.size());
        for (SurfaceType surfaceType : result) {
            assertEquals(SURFACE_TYPE, surfaceType);
        }
    }
}
