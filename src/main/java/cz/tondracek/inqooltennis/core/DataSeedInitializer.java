package cz.tondracek.inqooltennis.core;

import cz.tondracek.inqooltennis.common.model.Price;
import cz.tondracek.inqooltennis.court.data.CourtRepository;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeRepository;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataSeedInitializer {

    @Value("${seedEnabled}")
    private boolean seedEnabled;

    private final SurfaceTypeRepository surfaceTypeRepository;
    private final CourtRepository courtRepository;

    @PostConstruct
    public void init() {
        if (seedEnabled) {
            SurfaceTypeInit();
            CourtInit();
        }
    }

    SurfaceType tartar = new SurfaceType(
            UUID.randomUUID(),
            "Tartar",
            new Price(new BigDecimal("1.2"), "CZK"),
            false
    );

    SurfaceType grass = new SurfaceType(
            UUID.randomUUID(),
            "Grass",
            new Price(new BigDecimal("2.85"), "CZK"),
            false
    );

    Court basic0 = new Court(
            UUID.randomUUID(),
            "A basic cheap court 1",
            tartar,
            false
    );

    Court basic1 = new Court(
            UUID.randomUUID(),
            "A basic cheap court 2",
            tartar,
            false
    );

    Court premium0 = new Court(
            UUID.randomUUID(),
            "A Premium Court",
            grass,
            false
    );

    Court premium1 = new Court(
            UUID.randomUUID(),
            "An Extra Premium Court",
            grass,
            false
    );

    private void SurfaceTypeInit() {
        surfaceTypeRepository.create(tartar);
        surfaceTypeRepository.create(grass);
    }

    private void CourtInit() {
        courtRepository.create(basic0);
        courtRepository.create(basic1);
        courtRepository.create(premium0);
        courtRepository.create(premium1);
    }
}
