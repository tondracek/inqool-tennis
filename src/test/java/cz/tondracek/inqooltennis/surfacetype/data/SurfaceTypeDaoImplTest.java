package cz.tondracek.inqooltennis.surfacetype.data;

import cz.tondracek.inqooltennis.common.price.data.PriceEmbeddable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(SurfaceTypeDaoImpl.class)
class SurfaceTypeDaoImplTest {

    @Autowired
    SurfaceTypeDao surfaceTypeDao;

    @Autowired
    TestEntityManager entityManager;

    private final UUID sampleId = UUID.randomUUID();
    private final SurfaceTypeEntity sampleSurfaceTypeEntity = new SurfaceTypeEntity(
            sampleId,
            "Clay",
            new PriceEmbeddable(BigDecimal.valueOf(1f), "CZK"),
            false
    );
    private final SurfaceTypeEntity deletedEntity = new SurfaceTypeEntity(
            UUID.randomUUID(),
            "Inactive Surface",
            new PriceEmbeddable(BigDecimal.valueOf(2f), "CZK"),
            true
    );

    @Test
    void save() {
        surfaceTypeDao.save(sampleSurfaceTypeEntity);

        SurfaceTypeEntity result = entityManager.find(SurfaceTypeEntity.class, sampleId);

        assertNotNull(result);
        assertEquals(sampleSurfaceTypeEntity, result);
    }

    @Test
    void update() {
        surfaceTypeDao.save(sampleSurfaceTypeEntity);

        PriceEmbeddable updatedPrice = new PriceEmbeddable(BigDecimal.valueOf(123456789f), "UPDATED CZK");
        SurfaceTypeEntity update = new SurfaceTypeEntity(
                sampleId,
                "Updated Clay",
                updatedPrice,
                true
        );
        surfaceTypeDao.update(update);

        SurfaceTypeEntity result = entityManager.find(SurfaceTypeEntity.class, sampleId);

        assertNotNull(result);

        assertEquals("Updated Clay", result.getName());
        assertEquals(updatedPrice, result.getPricePerMinute());
        assertTrue(result.isDeleted());
    }

    @Test
    void findById() {
        entityManager.persist(sampleSurfaceTypeEntity);

        SurfaceTypeEntity result = surfaceTypeDao.findById(sampleId).orElse(null);

        assertNotNull(result);
        assertEquals(sampleSurfaceTypeEntity, result);
    }

    @Test
    void findAllActive() {
        Map<UUID, SurfaceTypeEntity> entities = Stream.of(
                generateSampleEntity(),
                generateSampleEntity(),
                generateSampleEntity(),
                deletedEntity
        ).collect(Collectors.toMap(SurfaceTypeEntity::getId, entity -> entity));

        for (SurfaceTypeEntity entity : entities.values()) {
            entityManager.persist(entity);
        }

        var result = surfaceTypeDao.findAllActive();

        assertNotNull(result);

        assertEquals(3, result.size());
        for (SurfaceTypeEntity surfaceType : result) {
            SurfaceTypeEntity original = entities.get(surfaceType.getId());

            assertEquals(original, surfaceType);
            assertFalse(surfaceType.isDeleted());
        }
    }

    private SurfaceTypeEntity generateSampleEntity() {
        return new SurfaceTypeEntity(
                UUID.randomUUID(),
                "Sample Surface",
                new PriceEmbeddable(BigDecimal.valueOf(10.0), "USD"),
                false
        );
    }
}