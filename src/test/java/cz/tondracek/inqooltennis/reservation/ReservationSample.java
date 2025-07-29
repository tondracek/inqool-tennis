package cz.tondracek.inqooltennis.reservation;

import cz.tondracek.inqooltennis.common.gametype.model.GameType;
import cz.tondracek.inqooltennis.common.price.PriceSample;
import cz.tondracek.inqooltennis.court.CourtSample;
import cz.tondracek.inqooltennis.customer.CustomerSample;
import cz.tondracek.inqooltennis.reservation.data.ReservationEntity;
import cz.tondracek.inqooltennis.reservation.dto.CreateReservationDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationDetailDto;
import cz.tondracek.inqooltennis.reservation.dto.UpdateReservationDto;
import cz.tondracek.inqooltennis.reservation.model.Reservation;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationSample {

    private static final UUID SAMPLE_ID = UUID.randomUUID();

    public static final CreateReservationDto CREATE_DTO = new CreateReservationDto(
            CourtSample.COURT.getId(),
            LocalDateTime.of(2023, 10, 1, 10, 0),
            LocalDateTime.of(2023, 10, 1, 11, 0),
            GameType.SINGLES,
            CustomerSample.CUSTOMER.getName(),
            CustomerSample.CUSTOMER.getPhoneNumber()
    );
    public static final Reservation RESERVATION = new Reservation(
            SAMPLE_ID,
            CourtSample.COURT,
            CustomerSample.CUSTOMER,
            LocalDateTime.of(2023, 10, 1, 10, 0),
            LocalDateTime.of(2023, 10, 1, 11, 0),
            PriceSample.CZK_100,
            GameType.SINGLES,
            false,
            LocalDateTime.now()
    );
    public static final Reservation RESERVATION_DELETED = RESERVATION.withDeleted(true);
    public static final ReservationEntity RESERVATION_ENTITY = new ReservationEntity(
            SAMPLE_ID,
            CourtSample.COURT_ENTITY,
            CustomerSample.CUSTOMER_ENTITY,
            LocalDateTime.of(2023, 10, 1, 10, 0),
            LocalDateTime.of(2023, 10, 1, 11, 0),
            PriceSample.CZK_100_EMBEDDABLE,
            GameType.SINGLES,
            false
    );
    public static final ReservationEntity RESERVATION_DELETED_ENTITY = new ReservationEntity(
            SAMPLE_ID,
            CourtSample.COURT_ENTITY,
            CustomerSample.CUSTOMER_ENTITY,
            LocalDateTime.of(2023, 10, 1, 10, 0),
            LocalDateTime.of(2023, 10, 1, 11, 0),
            PriceSample.CZK_100_EMBEDDABLE,
            GameType.SINGLES,
            true
    );
    public static final ReservationDetailDto RESERVATION_DTO = new ReservationDetailDto(
            SAMPLE_ID,
            CourtSample.COURT_DTO,
            CustomerSample.CUSTOMER_DTO,
            LocalDateTime.of(2023, 10, 1, 10, 0),
            LocalDateTime.of(2023, 10, 1, 11, 0),
            PriceSample.CZK_100_DTO,
            GameType.SINGLES,
            false,
            LocalDateTime.now()
    );

    public static final UpdateReservationDto UPDATE_DTO = new UpdateReservationDto(
            CourtSample.COURT_2.getId(),
            LocalDateTime.of(2023, 10, 2, 12, 0),
            LocalDateTime.of(2023, 10, 2, 13, 0),
            GameType.DOUBLES
    );
    public static final Reservation UPDATED_RESERVATION = new Reservation(
            SAMPLE_ID,
            CourtSample.COURT_2,
            CustomerSample.CUSTOMER,
            LocalDateTime.of(2023, 10, 2, 12, 0),
            LocalDateTime.of(2023, 10, 2, 13, 0),
            PriceSample.EUR_200,
            GameType.DOUBLES,
            false,
            LocalDateTime.now()
    );
    public static final ReservationEntity UPDATED_RESERVATION_ENTITY = new ReservationEntity(
            SAMPLE_ID,
            CourtSample.COURT_2_ENTITY,
            CustomerSample.CUSTOMER_ENTITY,
            LocalDateTime.of(2023, 10, 2, 12, 0),
            LocalDateTime.of(2023, 10, 2, 13, 0),
            PriceSample.EUR_200_EMBEDDABLE,
            GameType.DOUBLES,
            false
    );

    public static final UUID RESERVATION_2030_ID = UUID.randomUUID();
    public static final Reservation RESERVATION_2030 = new Reservation(
            RESERVATION_2030_ID,
            CourtSample.COURT_2,
            CustomerSample.CUSTOMER_2,
            LocalDateTime.of(2030, 9, 3, 14, 0),
            LocalDateTime.of(2030, 9, 3, 15, 0),
            PriceSample.EUR_100,
            GameType.DOUBLES,
            false,
            LocalDateTime.now()
    );
    public static final ReservationEntity RESERVATION_2030_ENTITY = new ReservationEntity(
            RESERVATION_2030_ID,
            CourtSample.COURT_2_ENTITY,
            CustomerSample.CUSTOMER_2_ENTITY,
            LocalDateTime.of(2030, 9, 3, 14, 0),
            LocalDateTime.of(2030, 9, 3, 15, 0),
            PriceSample.EUR_100_EMBEDDABLE,
            GameType.DOUBLES,
            false
    );
    public static final ReservationDetailDto RESERVATION_2030_DTO = new ReservationDetailDto(
            RESERVATION_2030_ID,
            CourtSample.COURT_2_DTO,
            CustomerSample.CUSTOMER_2_DTO,
            LocalDateTime.of(2030, 9, 3, 14, 0),
            LocalDateTime.of(2030, 9, 3, 15, 0),
            PriceSample.EUR_100_DTO,
            GameType.DOUBLES,
            false,
            LocalDateTime.now()
    );

    private static final UUID RESERVATION_2140_DELETED_ID = UUID.randomUUID();

    public static final ReservationEntity RESERVATION_2140_DELETED_ENTITY = new ReservationEntity(
            RESERVATION_2140_DELETED_ID,
            CourtSample.COURT_2_ENTITY,
            CustomerSample.CUSTOMER_2_ENTITY,
            LocalDateTime.of(2140, 9, 3, 14, 0),
            LocalDateTime.of(2140, 9, 3, 15, 0),
            PriceSample.EUR_100_EMBEDDABLE,
            GameType.DOUBLES,
            true
    );
}
