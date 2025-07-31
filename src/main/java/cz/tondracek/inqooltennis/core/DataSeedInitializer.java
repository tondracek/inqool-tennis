package cz.tondracek.inqooltennis.core;

import cz.tondracek.inqooltennis.common.password.PasswordEncoder;
import cz.tondracek.inqooltennis.common.price.model.Price;
import cz.tondracek.inqooltennis.court.data.CourtRepository;
import cz.tondracek.inqooltennis.court.model.Court;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeRepository;
import cz.tondracek.inqooltennis.surfacetype.model.SurfaceType;
import cz.tondracek.inqooltennis.user.data.UserRepository;
import cz.tondracek.inqooltennis.user.model.User;
import cz.tondracek.inqooltennis.user.model.UserRole;
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
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (seedEnabled) {
            SurfaceTypeInit();
            CourtInit();
            UsersInit();
        }
    }

    SurfaceType tartar = new SurfaceType(
            UUID.randomUUID(),
            "Tartar",
            new Price(BigDecimal.valueOf(1.2), "CZK"),
            false
    );

    SurfaceType grass = new SurfaceType(
            UUID.randomUUID(),
            "Grass",
            new Price(BigDecimal.valueOf(2.85), "CZK"),
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

    User admin = new User(
            UUID.randomUUID(),
            "admin@mail.com",
            PasswordEncoder.hash("admin"),
            UserRole.ADMIN,
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

    private void UsersInit() {
        userRepository.create(admin);
    }
}
