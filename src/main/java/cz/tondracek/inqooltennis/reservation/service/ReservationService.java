package cz.tondracek.inqooltennis.reservation.service;

import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.reservation.dto.CourtReservationListDto;
import cz.tondracek.inqooltennis.reservation.dto.CreateReservationDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationDetailDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationPreviewDto;
import cz.tondracek.inqooltennis.reservation.dto.UpdateReservationDto;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    /**
     * @param createDto values to create a new reservation
     * @return Details of the created reservation
     * @throws ConflictException if the reservation overlaps with an existing reservation
     */
    ReservationDetailDto createReservation(CreateReservationDto createDto);

    /**
     * @param id        Reservation ID to update
     * @param updateDto values to update
     * @return Updated reservation details
     * @throws NotFoundException if the reservation with the given ID does not exist
     * @throws ConflictException if the updated reservation overlaps with an existing reservation
     */
    ReservationDetailDto updateReservation(UUID id, UpdateReservationDto updateDto);

    /**
     * @param id Reservation ID to soft-delete (set deleted flag to true)
     * @throws NotFoundException if the reservation with the given ID does not exist
     */
    void softDeleteReservation(UUID id);


    /**
     * ⚠️ Can return deleted reservations (*deleted flag set to true).
     *
     * @param id Reservation ID to retrieve
     * @return Details of the reservation
     * @throws NotFoundException if the reservation with the given ID does not exist
     */
    ReservationDetailDto getReservationById(UUID id);

    /**
     * @return List of all reservations
     */
    List<ReservationPreviewDto> getActiveReservations();

    /**
     * @param courtId ID of the court to get reservations for
     * @return List of reservations for the specified court
     * @throws NotFoundException if the court with the given ID does not exist
     */
    CourtReservationListDto getCourtReservations(UUID courtId);

    /**
     * @param phoneNumber Phone number to search for reservations
     * @param onlyFuture  If true, only future reservations are returned
     * @return List of reservations matching the phone number
     */
    List<ReservationPreviewDto> getReservationsByPhoneNumber(String phoneNumber, boolean onlyFuture);
}
