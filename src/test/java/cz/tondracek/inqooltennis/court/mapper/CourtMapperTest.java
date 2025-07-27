package cz.tondracek.inqooltennis.court.mapper;

import cz.tondracek.inqooltennis.common.price.dto.PriceDto;
import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.court.dto.CourtDetailDto;
import cz.tondracek.inqooltennis.court.dto.CreateCourtDto;
import cz.tondracek.inqooltennis.court.dto.UpdateCourtDto;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CourtMapperTest {

    @Autowired
    CourtMapper courtMapper;

    SurfaceType sampleSurfaceType = new SurfaceType(
            UUID.randomUUID(),
            "Surface 1",
            new Price(BigDecimal.valueOf(2.1), "EUR"),
            false
    );
    SurfaceTypeDetailDto sampleSurfaceTypeDto = new SurfaceTypeDetailDto(
            sampleSurfaceType.getId(),
            "Surface 1",
            new PriceDto(BigDecimal.valueOf(2.1), "EUR"),
            false
    );

    Court sampleCourt = new Court(
            UUID.randomUUID(),
            "Original Court",
            sampleSurfaceType,
            false
    );

    CourtDetailDto sampleCourtDto = new CourtDetailDto(
            sampleCourt.getId(),
            "Original Court",
            sampleSurfaceTypeDto,
            false
    );

    @Test
    void createToCourt() {
        CreateCourtDto createCourtDto = new CreateCourtDto(
                "Court 1",
                sampleSurfaceType.getId()
        );

        UUID expectedId = UUID.randomUUID();
        Court expectedCourt = new Court(
                expectedId,
                "Court 1",
                sampleSurfaceType,
                false
        );

        Court result = courtMapper.toCourt(createCourtDto, expectedId, sampleSurfaceType, false);

        assertNotNull(result);
        assertEquals(expectedCourt, result);
    }

    @Test
    void updateToCourt() {
        SurfaceType updatedSurface = new SurfaceType(
                UUID.randomUUID(),
                "Updated Surface",
                new Price(BigDecimal.valueOf(3.5), "CZK"),
                false
        );
        UpdateCourtDto updateCourtDto = new UpdateCourtDto(
                "Updated Court",
                updatedSurface.getId()
        );

        Court expectedCourt = new Court(
                sampleCourt.getId(),
                "Updated Court",
                updatedSurface,
                false
        );

        Court result = courtMapper.toCourt(updateCourtDto, sampleCourt, updatedSurface);

        assertNotNull(result);
        assertEquals(expectedCourt, result);
    }

    @Test
    void toDetailDto() {
        CourtDetailDto result = courtMapper.toDetailDto(sampleCourt);

        assertNotNull(result);
        assertEquals(sampleCourtDto, result);
    }
}