package cz.tondracek.inqooltennis.reservation.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.reservation.model.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository {

    /**
     * @param reservation the reservation to be created
     * @return the created reservation
     */
    Reservation create(Reservation reservation);

    /**
     * @param reservation the reservation to be updated
     * @return the updated reservation
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    Reservation update(Reservation reservation);

    /**
     * ⚠️ Can return court with deleted=true
     *
     * @param id the UUID of the reservation to find
     * @return the reservation with the given ID, if it exists
     * @throws NotFoundException if the customer with the given ID does not exist
     */
    Reservation findById(UUID id);

    /**
     * @return a list of all non-deleted reservations
     */
    List<Reservation> findAllActive();

    /**
     * @param courtId the UUID of the court to find reservations for
     * @return a list of all non-deleted reservations for the given court
     */
    List<Reservation> findActiveByCourtId(UUID courtId);

    /**
     * @param phoneNumber the phone number to find reservations for
     * @return a list of all non-deleted reservations for the given phone number
     */
    List<Reservation> findActiveByPhoneNumber(String phoneNumber);

    /**
     * @param phoneNumber the phone number to find future reservations for
     * @return a list of all non-deleted future reservations for the given phone number
     */
    List<Reservation> findActiveFutureByPhoneNumber(String phoneNumber);
}
