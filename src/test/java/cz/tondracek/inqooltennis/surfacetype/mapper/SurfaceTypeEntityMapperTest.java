package cz.tondracek.inqooltennis.surfacetype.mapper;

import cz.tondracek.inqooltennis.common.price.data.PriceEmbeddable;
import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeEntity;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class SurfaceTypeEntityMapperTest {

    @Autowired
    @Qualifier("surfaceTypeEntityMapperImpl")
    SurfaceTypeEntityMapper mapper;

    @Test
    void toEntity_ShouldMapCorrectly() {
        SurfaceType surfaceType = new SurfaceType(
                UUID.randomUUID(),
                "Surface 1",
                new Price(BigDecimal.valueOf(2.1), "EUR"),
                false
        );

        SurfaceTypeEntity surfaceTypeEntity = mapper.toEntity(surfaceType);

        assert surfaceTypeEntity != null;

        assert surfaceTypeEntity.getId() == surfaceType.getId();
        assert surfaceTypeEntity.getName().equals(surfaceType.getName());

        assert surfaceTypeEntity.getPricePerMinute().getAmount().equals(surfaceType.getPricePerMinute().getAmount());
        assert surfaceTypeEntity.getPricePerMinute().getCurrencyCode().equals(surfaceType.getPricePerMinute().getCurrencyCode());

        assert surfaceTypeEntity.isDeleted() == surfaceType.isDeleted();
    }

    @Test
    void toModel_ShouldMapCorrectly() {
        SurfaceTypeEntity surfaceTypeEntity = new SurfaceTypeEntity(
                UUID.randomUUID(),
                "Surface 1",
                new PriceEmbeddable(BigDecimal.valueOf(2.3), "EUR"),
                false
        );

        SurfaceType surfaceType = mapper.toModel(surfaceTypeEntity);

        assert surfaceType != null;

        assert surfaceType.getId() == surfaceTypeEntity.getId();
        assert surfaceType.getName().equals(surfaceTypeEntity.getName());

        assert surfaceType.getPricePerMinute().getAmount().equals(surfaceTypeEntity.getPricePerMinute().getAmount());
        assert surfaceType.getPricePerMinute().getCurrencyCode().equals(surfaceTypeEntity.getPricePerMinute().getCurrencyCode());

        assert surfaceType.isDeleted() == surfaceTypeEntity.isDeleted();
    }
}