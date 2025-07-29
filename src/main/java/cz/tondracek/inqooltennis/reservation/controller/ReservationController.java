package cz.tondracek.inqooltennis.reservation.controller;

import cz.tondracek.inqooltennis.reservation.dto.CourtReservationListDto;
import cz.tondracek.inqooltennis.reservation.dto.CreateReservationDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationDetailDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationPreviewDto;
import cz.tondracek.inqooltennis.reservation.dto.UpdateReservationDto;
import cz.tondracek.inqooltennis.reservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDetailDto> createReservation(
            @Valid @RequestBody CreateReservationDto dto
    ) {
        return ResponseEntity.ok(reservationService.createReservation(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDetailDto> updateReservation(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateReservationDto dto
    ) {
        return ResponseEntity.ok(reservationService.updateReservation(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) {
        reservationService.softDeleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDetailDto> getReservationById(@PathVariable UUID id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationPreviewDto>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getActiveReservations());
    }

    @GetMapping("/court/{courtId}")
    public ResponseEntity<CourtReservationListDto> getReservationsByCourtId(@PathVariable UUID courtId) {
        return ResponseEntity.ok(reservationService.getCourtReservations(courtId));
    }

    @GetMapping("/phone/{phoneNumber}/futureOnly/{futureOnly}")
    public ResponseEntity<List<ReservationPreviewDto>> getReservationsByPhoneNumber(
            @PathVariable String phoneNumber,
            @PathVariable boolean futureOnly
    ) {
        return ResponseEntity.ok(reservationService.getReservationsByPhoneNumber(phoneNumber, futureOnly));
    }
}
