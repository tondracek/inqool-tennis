package cz.tondracek.inqooltennis.reservation.data;

import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_2030_ENTITY;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_2140_DELETED_ENTITY;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_ENTITY;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.UPDATED_RESERVATION_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(ReservationDaoImpl.class)
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    TestEntityManager entityManager;

    private void saveReservationComponents(ReservationEntity reservation) {
        if (reservationDao.findById(reservation.getCourt().getSurfaceType().getId()).isEmpty())
            entityManager.persist(reservation.getCourt().getSurfaceType());
        if (reservationDao.findById(reservation.getCourt().getId()).isEmpty())
            entityManager.persist(reservation.getCourt());
        if (reservationDao.findById(reservation.getCustomer().getId()).isEmpty())
            entityManager.persist(reservation.getCustomer());
    }

    @Test
    void save() {
        saveReservationComponents(RESERVATION_ENTITY);
        reservationDao.save(RESERVATION_ENTITY);

        var result = entityManager.find(ReservationEntity.class, RESERVATION_ENTITY.getId());

        assertNotNull(result);
        assertEquals(RESERVATION_ENTITY, result);
    }

    @Test
    void update() {
        saveReservationComponents(RESERVATION_ENTITY);
        reservationDao.save(RESERVATION_ENTITY);

        saveReservationComponents(UPDATED_RESERVATION_ENTITY);
        reservationDao.update(UPDATED_RESERVATION_ENTITY);

        var result = entityManager.find(ReservationEntity.class, UPDATED_RESERVATION_ENTITY.getId());
        assertEquals(UPDATED_RESERVATION_ENTITY, result);
    }

    @Test
    void findById() {
        saveReservationComponents(RESERVATION_ENTITY);
        reservationDao.save(RESERVATION_ENTITY);

        Optional<ReservationEntity> found = reservationDao.findById(RESERVATION_ENTITY.getId());

        assertTrue(found.isPresent());
        assertEquals(RESERVATION_ENTITY.getId(), found.get().getId());
    }

    @Test
    void findById_shouldReturnEmptyIfNotFound() {
        Optional<ReservationEntity> found = reservationDao.findById(RESERVATION_ENTITY.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    void findAllActive_shouldReturnOnlyNotDeletedReservations() {
        saveReservationComponents(RESERVATION_ENTITY);
        entityManager.persist(RESERVATION_ENTITY);

        saveReservationComponents(RESERVATION_2030_ENTITY);
        entityManager.persist(RESERVATION_2030_ENTITY);

        saveReservationComponents(RESERVATION_2140_DELETED_ENTITY);
        entityManager.persist(RESERVATION_2140_DELETED_ENTITY);

        List<ReservationEntity> activeReservations = reservationDao.findAllActive();

        Json.prettyPrint(activeReservations);

        assertEquals(2, activeReservations.size());
        assertTrue(activeReservations.stream().noneMatch(ReservationEntity::isDeleted));
    }
}
