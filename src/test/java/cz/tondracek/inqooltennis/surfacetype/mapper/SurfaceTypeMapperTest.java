package cz.tondracek.inqooltennis.surfacetype.mapper;

import cz.tondracek.inqooltennis.common.price.dto.PriceDto;
import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.surfacetype.dto.CreateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.dto.UpdateSurfaceTypeDto;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class SurfaceTypeMapperTest {

    @Autowired
    @Qualifier("surfaceTypeMapperImpl")
    SurfaceTypeMapper mapper;

    @Test
    void createToSurfaceType_ShouldMapCorrectly() {
        UUID id = UUID.randomUUID();
        CreateSurfaceTypeDto dto = new CreateSurfaceTypeDto(
                "New technology surface type",
                new PriceDto(BigDecimal.valueOf(200.0), "EUR")
        );

        SurfaceType surfaceType = mapper.toSurfaceType(dto, id, false);

        assert surfaceType != null;

        assert surfaceType.getId().equals(id);
        assert surfaceType.getName().equals(dto.getName());

        assert surfaceType.getPricePerMinute().getAmount().equals(dto.getPricePerMinute().getAmount());
        assert surfaceType.getPricePerMinute().getCurrencyCode().equals(dto.getPricePerMinute().getCurrencyCode());
    }

    @Test
    void updateToSurfaceType_ShouldMapCorrectly() {
        UUID id = UUID.randomUUID();
        SurfaceType original = new SurfaceType(
                id,
                "Old surface type",
                new Price(BigDecimal.valueOf(100.0), "USD"),
                false
        );

        UpdateSurfaceTypeDto update = new UpdateSurfaceTypeDto(
                "Updated surface type",
                new PriceDto(BigDecimal.valueOf(150.0), "EUR")
        );

        SurfaceType updatedSurfaceType = mapper.toSurfaceType(update, original);

        assert updatedSurfaceType != null;

        assert updatedSurfaceType.getId().equals(id);
        assert updatedSurfaceType.getName().equals(update.getName());

        assert updatedSurfaceType.getPricePerMinute().getAmount().equals(update.getPricePerMinute().getAmount());
        assert updatedSurfaceType.getPricePerMinute().getCurrencyCode().equals(update.getPricePerMinute().getCurrencyCode());

        assert updatedSurfaceType.isDeleted() == original.isDeleted();
    }

    @Test
    void updateToSurfaceType_ShouldMapCorrectlyWithDeletedTrue() {
        UUID id = UUID.randomUUID();
        SurfaceType original = new SurfaceType(
                id,
                "Old surface type",
                new Price(BigDecimal.valueOf(100.0), "USD"),
                true
        );

        UpdateSurfaceTypeDto update = new UpdateSurfaceTypeDto(
                "Updated surface type",
                new PriceDto(BigDecimal.valueOf(150.0), "EUR")
        );

        SurfaceType updatedSurfaceType = mapper.toSurfaceType(update, original);

        assert updatedSurfaceType != null;

        assert updatedSurfaceType.getId().equals(id);
        assert updatedSurfaceType.getName().equals(update.getName());

        assert updatedSurfaceType.getPricePerMinute().getAmount().equals(update.getPricePerMinute().getAmount());
        assert updatedSurfaceType.getPricePerMinute().getCurrencyCode().equals(update.getPricePerMinute().getCurrencyCode());

        assert updatedSurfaceType.isDeleted() == original.isDeleted();
    }

    @Test
    void toDetailDtoShouldMapCorrectly() {
        UUID id = UUID.randomUUID();
        SurfaceType surfaceType = new SurfaceType(
                id,
                "Detail surface type",
                new Price(BigDecimal.valueOf(300.0), "USD"),
                false
        );

        SurfaceTypeDetailDto detailDto = mapper.toDetailDto(surfaceType);

        assert detailDto != null;

        assert detailDto.getId().equals(id);
        assert detailDto.getName().equals(surfaceType.getName());

        assert detailDto.getPricePerMinute().getAmount().equals(surfaceType.getPricePerMinute().getAmount());
        assert detailDto.getPricePerMinute().getCurrencyCode().equals(surfaceType.getPricePerMinute().getCurrencyCode());

        assert detailDto.isDeleted() == surfaceType.isDeleted();
    }
}