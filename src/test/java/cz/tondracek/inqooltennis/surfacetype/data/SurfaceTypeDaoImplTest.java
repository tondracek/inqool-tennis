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

import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(SurfaceTypeDaoImpl.class)
class SurfaceTypeDaoImplTest {

    @Autowired
    SurfaceTypeDao surfaceTypeDao;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void save() {
        surfaceTypeDao.save(SURFACE_TYPE_ENTITY);

        SurfaceTypeEntity result = entityManager.find(SurfaceTypeEntity.class, SURFACE_TYPE_ENTITY.getId());

        assertNotNull(result);
        assertEquals(SURFACE_TYPE_ENTITY, result);
    }

    @Test
    void update() {
        surfaceTypeDao.save(SURFACE_TYPE_ENTITY);

        PriceEmbeddable updatedPrice = new PriceEmbeddable(BigDecimal.valueOf(123456789f), "UPDATED CZK");
        SurfaceTypeEntity update = new SurfaceTypeEntity(
                SURFACE_TYPE_ENTITY.getId(),
                "Updated Clay",
                updatedPrice,
                true
        );
        surfaceTypeDao.update(update);

        SurfaceTypeEntity result = entityManager.find(SurfaceTypeEntity.class, SURFACE_TYPE_ENTITY.getId());

        assertNotNull(result);
        assertEquals(update, result);
    }

    @Test
    void findById() {
        entityManager.persist(SURFACE_TYPE_ENTITY);

        SurfaceTypeEntity result = surfaceTypeDao.findById(SURFACE_TYPE_ENTITY.getId()).orElse(null);

        assertEquals(SURFACE_TYPE_ENTITY, result);
    }

    @Test
    void findAllActive() {
        Map<UUID, SurfaceTypeEntity> entities = Stream.of(
                generateSampleEntity(false),
                generateSampleEntity(false),
                generateSampleEntity(false),
                generateSampleEntity(true),
                generateSampleEntity(true)
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

    private SurfaceTypeEntity generateSampleEntity(boolean deleted) {
        return new SurfaceTypeEntity(
                UUID.randomUUID(),
                "Sample Surface",
                new PriceEmbeddable(BigDecimal.valueOf(10.0), "USD"),
                deleted
        );
    }
}