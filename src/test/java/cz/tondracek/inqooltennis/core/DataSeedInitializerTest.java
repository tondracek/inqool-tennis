package cz.tondracek.inqooltennis.core;

import cz.tondracek.inqooltennis.court.data.CourtRepository;
import cz.tondracek.inqooltennis.surfacetype.data.SurfaceTypeRepository;
import cz.tondracek.inqooltennis.user.data.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestPropertySource(properties = "seedEnabled=true")
class DataSeedInitializerTest {

    @MockBean
    private SurfaceTypeRepository surfaceTypeRepository;

    @MockBean
    private CourtRepository courtRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private DataSeedInitializer initializer;

    @Test
    void init() {
        verify(surfaceTypeRepository, atLeastOnce()).create(any());
        verify(courtRepository, atLeastOnce()).create(any());
        verify(userRepository, atLeastOnce()).create(any());
    }
}