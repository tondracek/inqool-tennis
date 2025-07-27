package cz.tondracek.inqooltennis.court.mapper;

import cz.tondracek.inqooltennis.common.price.data.PriceEmbeddable;
import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.court.data.CourtEntity;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeEntity;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CourtEntityMapperTest {

    @Autowired
    CourtEntityMapper courtEntityMapper;

    UUID surfaceId = UUID.randomUUID();
    SurfaceType surfaceType = new SurfaceType(
            surfaceId,
            "Surface 1",
            new Price(BigDecimal.valueOf(2.1), "EUR"),
            false
    );
    SurfaceTypeEntity surfaceTypeEntity = new SurfaceTypeEntity(
            surfaceId,
            "Surface 1",
            new PriceEmbeddable(BigDecimal.valueOf(2.1), "EUR"),
            false
    );

    UUID courtId = UUID.randomUUID();
    Court court = new Court(
            courtId,
            "Test Court",
            surfaceType,
            false
    );
    CourtEntity courtEntity = new CourtEntity(
            courtId,
            "Test Court",
            surfaceTypeEntity,
            false
    );

    @Test
    void toEntity() {
        CourtEntity result = courtEntityMapper.toEntity(court);

        assertNotNull(result);
        assertEquals(result, courtEntity);
    }

    @Test
    void toModel() {
        Court result = courtEntityMapper.toModel(courtEntity);

        assertNotNull(result);
        assertEquals(result, court);
    }
}