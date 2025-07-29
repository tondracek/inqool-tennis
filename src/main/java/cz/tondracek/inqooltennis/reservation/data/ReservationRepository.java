package cz.tondracek.inqooltennis.reservation.data;

import cz.tondracek.inqooltennis.reservation.model.Reservation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository {

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    @NotNull
    Reservation findById(UUID id);

    List<Reservation> findAllActive();

    List<Reservation> findActiveByCourtId(UUID courtId);

    List<Reservation> findActiveByPhoneNumber(String phoneNumber);

    List<Reservation> findActiveFutureByPhoneNumber(String phoneNumber);
}
