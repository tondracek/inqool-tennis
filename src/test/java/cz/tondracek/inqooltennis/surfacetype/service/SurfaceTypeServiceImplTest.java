package cz.tondracek.inqooltennis.surfacetype.service;

import cz.tondracek.inqooltennis.common.price.dto.PriceDto;
import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeRepository;
import cz.tondracek.inqooltennis.surfacetype.dto.CreateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.dto.UpdateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.mapper.SurfaceTypeMapper;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    private static final Price EUR_100 = new Price(BigDecimal.valueOf(100f), "EUR");
    private static final PriceDto EUR_100_DTO = new PriceDto(BigDecimal.valueOf(100f), "EUR");

    private static final Price EUR_200 = new Price(BigDecimal.valueOf(200f), "EUR");
    private static final PriceDto EUR_200_DTO = new PriceDto(BigDecimal.valueOf(200f), "EUR");

    private static final Price CZK_100 = new Price(BigDecimal.valueOf(100f), "CZK");
    private static final PriceDto CZK_100_DTO = new PriceDto(BigDecimal.valueOf(100f), "CZK");

    @Test
    void createSurfaceType_shouldReturnCorrectly() {
        UUID uuid = UUID.randomUUID();
        CreateSurfaceTypeDto createDto = new CreateSurfaceTypeDto("Clay", EUR_100_DTO);

        SurfaceType expectedModel = new SurfaceType(uuid, "Clay", EUR_100, false);
        SurfaceTypeDetailDto expectedDto = new SurfaceTypeDetailDto(uuid, "Clay", EUR_100_DTO, false);

        when(mapper.toSurfaceType(eq(createDto), any(UUID.class), eq(false))).thenReturn(expectedModel);
        when(repository.create(eq(expectedModel))).thenReturn(expectedModel);
        when(mapper.toDetailDto(eq(expectedModel))).thenReturn(expectedDto);

        SurfaceTypeDetailDto result = service.createSurfaceType(createDto);

        assertEquals(expectedDto, result);
    }

    @Test
    void updateSurfaceType_shouldReturnCorrectly() {
        UUID uuid = UUID.randomUUID();

        UpdateSurfaceTypeDto updateDto = new UpdateSurfaceTypeDto("Grass", CZK_100_DTO);
        SurfaceType originalModel = new SurfaceType(uuid, "Clay", EUR_200, false);
        SurfaceType updatedModel = new SurfaceType(uuid, "Grass", CZK_100, false);

        SurfaceTypeDetailDto expectedDto = new SurfaceTypeDetailDto(uuid, "Grass", CZK_100_DTO, false);

        when(repository.findById(eq(uuid))).thenReturn(originalModel);
        when(mapper.toSurfaceType(eq(updateDto), eq(originalModel))).thenReturn(updatedModel);
        when(repository.update(eq(updatedModel))).thenReturn(updatedModel);
        when(mapper.toDetailDto(eq(updatedModel))).thenReturn(expectedDto);

        SurfaceTypeDetailDto result = service.updateSurfaceType(uuid, updateDto);

        assertEquals(expectedDto, result);
    }

    @Test
    void updateSurfaceType_shouldFailOnOriginalDeleted() {
        UUID uuid = UUID.randomUUID();

        UpdateSurfaceTypeDto updateDto = new UpdateSurfaceTypeDto("Grass", CZK_100_DTO);
        SurfaceType originalModel = new SurfaceType(uuid, "Clay", EUR_200, true);

        when(repository.findById(eq(uuid))).thenReturn(originalModel);

        assertThrows(NotFoundException.class, () -> service.updateSurfaceType(uuid, updateDto));
    }

    @Test
    void getAllSurfaceTypes_shouldReturnCorrectly() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        SurfaceType surfaceType1 = new SurfaceType(uuid1, "Clay", EUR_100, false);
        SurfaceType surfaceType2 = new SurfaceType(uuid2, "Grass", CZK_100, false);

        SurfaceTypeDetailDto dto1 = new SurfaceTypeDetailDto(uuid1, "Clay", EUR_100_DTO, false);
        SurfaceTypeDetailDto dto2 = new SurfaceTypeDetailDto(uuid2, "Grass", CZK_100_DTO, false);

        when(repository.findAllActive()).thenReturn(List.of(surfaceType1, surfaceType2));
        when(mapper.toDetailDto(surfaceType1)).thenReturn(dto1);
        when(mapper.toDetailDto(surfaceType2)).thenReturn(dto2);

        List<SurfaceTypeDetailDto> result = service.getAllSurfaceTypes();

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }

    @Test
    void softDeleteSurfaceType_shouldUpdateCorrectly() {
        UUID uuid = UUID.randomUUID();
        SurfaceType original = new SurfaceType(uuid, "Clay", EUR_100, false);
        SurfaceType updated = original.withDeleted(true);

        when(repository.findById(eq(uuid))).thenReturn(original);
        when(repository.update(eq(updated))).thenReturn(updated);

        service.softDeleteSurfaceType(uuid);

        Mockito.verify(repository).update(updated);

        assertEquals(uuid, updated.getId());
        assertEquals("Clay", updated.getName());
        assertEquals(EUR_100, updated.getPricePerMinute());
        assertTrue(updated.isDeleted());
    }
}