package cz.tondracek.inqooltennis.reservation.mapper;

import cz.tondracek.inqooltennis.reservation.data.ReservationEntity;
import cz.tondracek.inqooltennis.reservation.model.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_2000;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_2000_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ReservationEntityMapperTest {

    @Autowired
    ReservationEntityMapper reservationEntityMapper;

    @Test
    void toEntity() {
        ReservationEntity result = reservationEntityMapper.toEntity(RESERVATION_2000);

        assertNotNull(result);

        assertEquals(RESERVATION_2000_ENTITY.getId(), result.getId());
        assertEquals(RESERVATION_2000_ENTITY.getCourt().getId(), result.getCourt().getId());
        assertEquals(RESERVATION_2000_ENTITY.getCustomer().getId(), result.getCustomer().getId());
        assertEquals(RESERVATION_2000_ENTITY.getStartTime(), result.getStartTime());
        assertEquals(RESERVATION_2000_ENTITY.getEndTime(), result.getEndTime());
        assertEquals(RESERVATION_2000_ENTITY.getPrice(), result.getPrice());
        assertEquals(RESERVATION_2000_ENTITY.getGameType(), result.getGameType());
        assertEquals(RESERVATION_2000_ENTITY.isDeleted(), result.isDeleted());
    }

    @Test
    void toModel() {
        Reservation result = reservationEntityMapper.toModel(RESERVATION_2000_ENTITY);

        assertNotNull(result);

        assertEquals(RESERVATION_2000.getId(), result.getId());
        assertEquals(RESERVATION_2000.getCourt().getId(), result.getCourt().getId());
        assertEquals(RESERVATION_2000.getCustomer().getId(), result.getCustomer().getId());
        assertEquals(RESERVATION_2000.getStartTime(), result.getStartTime());
        assertEquals(RESERVATION_2000.getEndTime(), result.getEndTime());
        assertEquals(RESERVATION_2000.getPrice(), result.getPrice());
        assertEquals(RESERVATION_2000.getGameType(), result.getGameType());
        assertEquals(RESERVATION_2000.isDeleted(), result.isDeleted());
    }
}
