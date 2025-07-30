package cz.tondracek.inqooltennis.reservation.mapper;

import cz.tondracek.inqooltennis.reservation.dto.ReservationDetailDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationPreviewDto;
import cz.tondracek.inqooltennis.reservation.model.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static cz.tondracek.inqooltennis.reservation.ReservationSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_DTO;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_PREVIEW_DTO;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.UPDATED_RESERVATION;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.UPDATE_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ReservationMapperTest {

    @Autowired
    ReservationMapper reservationMapper;

    @Test
    void createToReservation() {
        Reservation result = reservationMapper.toReservation(
                CREATE_DTO,
                RESERVATION.getCustomer(),
                RESERVATION.getPrice(),
                RESERVATION.getCourt()
        );

        assertNotNull(result);

        assertEquals(RESERVATION.getCourt(), result.getCourt());
        assertEquals(RESERVATION.getCustomer(), result.getCustomer());
        assertEquals(RESERVATION.getStartTime(), result.getStartTime());
        assertEquals(RESERVATION.getEndTime(), result.getEndTime());
        assertEquals(RESERVATION.getPrice(), result.getPrice());
        assertEquals(RESERVATION.getGameType(), result.getGameType());
        assertEquals(RESERVATION.isDeleted(), result.isDeleted());
    }

    @Test
    void updateToReservation() {
        Reservation result = reservationMapper.toReservation(
                UPDATE_DTO,
                RESERVATION,
                UPDATED_RESERVATION.getPrice(),
                UPDATED_RESERVATION.getCourt()
        );

        assertNotNull(result);

        assertEquals(UPDATED_RESERVATION.getId(), result.getId());
        assertEquals(UPDATED_RESERVATION.getCourt(), result.getCourt());
        assertEquals(UPDATED_RESERVATION.getCustomer(), result.getCustomer());
        assertEquals(UPDATED_RESERVATION.getStartTime(), result.getStartTime());
        assertEquals(UPDATED_RESERVATION.getEndTime(), result.getEndTime());
        assertEquals(UPDATED_RESERVATION.getPrice(), result.getPrice());
        assertEquals(UPDATED_RESERVATION.getGameType(), result.getGameType());
        assertEquals(UPDATED_RESERVATION.isDeleted(), result.isDeleted());
    }

    @Test
    void toDetailDto() {
        ReservationDetailDto dto = reservationMapper.toDetailDto(RESERVATION);

        assertNotNull(dto);

        assertEquals(RESERVATION_DTO.id(), dto.id());
        assertEquals(RESERVATION_DTO.courtDetailDto().getId(), dto.courtDetailDto().getId());
        assertEquals(RESERVATION_DTO.customerDetailDto().id(), dto.customerDetailDto().id());
        assertEquals(RESERVATION_DTO.startTime(), dto.startTime());
        assertEquals(RESERVATION_DTO.endTime(), dto.endTime());
        assertEquals(RESERVATION_DTO.price(), dto.price());
        assertEquals(RESERVATION_DTO.gameType(), dto.gameType());
        assertEquals(RESERVATION_DTO.deleted(), dto.deleted());
        assertEquals(RESERVATION_DTO.createdAt().toLocalDate(), dto.createdAt().toLocalDate());
    }

    @Test
    void toPreviewDto() {
        ReservationPreviewDto dto = reservationMapper.toPreviewDto(RESERVATION);

        assertNotNull(dto);

        assertEquals(RESERVATION_PREVIEW_DTO.id(), dto.id());
        assertEquals(RESERVATION_PREVIEW_DTO.courtName(), dto.courtName());
        assertEquals(RESERVATION_PREVIEW_DTO.customerName(), dto.customerName());
        assertEquals(RESERVATION_PREVIEW_DTO.startTime(), dto.startTime());
        assertEquals(RESERVATION_PREVIEW_DTO.endTime(), dto.endTime());
        assertEquals(RESERVATION_PREVIEW_DTO.courtName(), dto.courtName());
        assertEquals(RESERVATION_PREVIEW_DTO.customerName(), dto.customerName());
    }
}
