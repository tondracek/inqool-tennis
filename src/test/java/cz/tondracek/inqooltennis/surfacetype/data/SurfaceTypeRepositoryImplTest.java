package cz.tondracek.inqooltennis.surfacetype.data;

import cz.tondracek.inqooltennis.common.price.data.PriceEmbeddable;
import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.surfacetype.mapper.SurfaceTypeEntityMapper;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


    UUID sampleId = UUID.randomUUID();
    SurfaceType sampleSurfaceType = new SurfaceType(
            sampleId,
            "Clay",
            new Price(BigDecimal.valueOf(1f), "CZK"),
            false
    );
    SurfaceTypeEntity sampleSurfaceTypeEntity = new SurfaceTypeEntity(
            sampleId,
            "Clay",
            new PriceEmbeddable(BigDecimal.valueOf(1f), "CZK"),
            false
    );

    @Test
    void create() {
        when(mapper.toEntity(sampleSurfaceType)).thenReturn(sampleSurfaceTypeEntity);

        SurfaceType result = repository.create(sampleSurfaceType);

        assertEquals(sampleSurfaceType, result);
    }

    @Test
    void update() {
        when(mapper.toEntity(sampleSurfaceType)).thenReturn(sampleSurfaceTypeEntity);

        SurfaceType result = repository.update(sampleSurfaceType);

        assertEquals(sampleSurfaceType, result);
    }

    @Test
    void findById_shouldReturnCorrectly() {
        when(dao.findById(any(UUID.class))).thenReturn(Optional.of(sampleSurfaceTypeEntity));
        when(mapper.toModel(sampleSurfaceTypeEntity)).thenReturn(sampleSurfaceType);

        SurfaceType result = repository.findById(sampleId);
        assertEquals(sampleSurfaceType, result);
    }

    @Test
    void findById_shouldFailOnNotFound() {
        when(dao.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> repository.findById(sampleId));
    }

    @Test
    void findAllActive() {
        when(dao.findAllActive()).thenReturn(List.of(sampleSurfaceTypeEntity, sampleSurfaceTypeEntity, sampleSurfaceTypeEntity));
        when(mapper.toModel(sampleSurfaceTypeEntity)).thenReturn(sampleSurfaceType);

        var result = repository.findAllActive();

        assertEquals(3, result.size());
        for (SurfaceType surfaceType : result) {
            assertEquals(sampleSurfaceType, surfaceType);
        }
    }
}