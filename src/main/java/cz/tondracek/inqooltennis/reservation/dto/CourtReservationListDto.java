package cz.tondracek.inqooltennis.reservation.dto;

import cz.tondracek.inqooltennis.court.dto.CourtDetailDto;

import java.util.List;

public record CourtReservationListDto(
        CourtDetailDto court,
        List<ReservationPreviewDto> reservations
) {
}
