package cz.tondracek.inqooltennis.core;

import cz.tondracek.inqooltennis.common.model.Price;
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

    @PostConstruct
    public void init() {
        System.out.println("DataSeedInitializer.init() called with seedEnabled: " + seedEnabled);
        if (seedEnabled) {
            SurfaceTypeInit();
            CourtInit();
        }
    }

    private void SurfaceTypeInit() {
        SurfaceType tartar = new SurfaceType(
                UUID.randomUUID(),
                "Tartar",
                new Price(new BigDecimal("1.2"), "CZK"),
                false
        );

        surfaceTypeRepository.create(tartar);

        SurfaceType grass = new SurfaceType(
                UUID.randomUUID(),
                "Grass",
                new Price(new BigDecimal("2.85"), "CZK"),
                false
        );

        surfaceTypeRepository.create(grass);
    }

    private void CourtInit() {
        // TODO
    }
}
