package cz.tondracek.inqooltennis.court.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static cz.tondracek.inqooltennis.court.CourtSample.COURT_2_DELETED_ENTITY;
import static cz.tondracek.inqooltennis.court.CourtSample.COURT_ENTITY;
import static cz.tondracek.inqooltennis.court.CourtSample.UPDATED_COURT_ENTITY;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(CourtDaoImpl.class)
class CourtDaoImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourtDao courtDao;

    @Test
    void save() {
        entityManager.persist(COURT_ENTITY.getSurfaceType());
        courtDao.save(COURT_ENTITY);
        CourtEntity result = entityManager.find(CourtEntity.class, COURT_ENTITY.getId());

        System.out.println("Saved CourtEntity: " + COURT_ENTITY);
        assertEquals(COURT_ENTITY, result);
    }

    @Test
    void update() {
        assertEquals(COURT_ENTITY.getId(), UPDATED_COURT_ENTITY.getId());

        entityManager.persist(COURT_ENTITY.getSurfaceType());
        entityManager.persist(UPDATED_COURT_ENTITY.getSurfaceType());

        entityManager.persist(COURT_ENTITY);
        courtDao.update(UPDATED_COURT_ENTITY);
        CourtEntity result = entityManager.find(CourtEntity.class, UPDATED_COURT_ENTITY.getId());

        assertEquals(UPDATED_COURT_ENTITY, result);
    }

    @Test
    void findById() {
        entityManager.persist(COURT_ENTITY.getSurfaceType());
        entityManager.persist(COURT_ENTITY);
        CourtEntity result = courtDao.findById(COURT_ENTITY.getId()).orElseThrow();

        assertEquals(COURT_ENTITY, result);
    }

    @Test
    void findActiveById() {
        entityManager.persist(COURT_ENTITY.getSurfaceType());
        entityManager.persist(COURT_ENTITY);

        entityManager.persist(COURT_2_DELETED_ENTITY.getSurfaceType());
        entityManager.persist(COURT_2_DELETED_ENTITY);

        CourtEntity result = courtDao.findActiveById(COURT_ENTITY.getId()).orElseThrow();

        assertEquals(COURT_ENTITY, result);
    }

    @Test
    void findActiveById_shouldNotReturnDeleted() {
        entityManager.persist(COURT_ENTITY.getSurfaceType());
        entityManager.persist(COURT_ENTITY);

        entityManager.persist(COURT_2_DELETED_ENTITY.getSurfaceType());
        entityManager.persist(COURT_2_DELETED_ENTITY);

        Optional<CourtEntity> result = courtDao.findActiveById(COURT_2_DELETED_ENTITY.getId());

        assertTrue(result.isEmpty(), "Deleted court should not be found as active");
    }

    @Test
    void findAllActive() {
        List<CourtEntity> entities = List.of(
                generateCourtEntity(false),
                generateCourtEntity(false),
                generateCourtEntity(false),
                generateCourtEntity(true),
                generateCourtEntity(true)
        );

        entityManager.persist(SURFACE_TYPE_ENTITY);
        entities.forEach(entityManager::persist);

        var result = courtDao.findAllActive();

        assertEquals(3, result.size());

        assertTrue(result.contains(entities.get(0)));
        assertTrue(result.contains(entities.get(1)));
        assertTrue(result.contains(entities.get(2)));
    }

    private CourtEntity generateCourtEntity(boolean deleted) {
        return new CourtEntity(
                UUID.randomUUID(),
                "Test Court",
                SURFACE_TYPE_ENTITY,
                deleted
        );
    }
}