package cz.tondracek.inqooltennis.reservation.dto;

import cz.tondracek.inqooltennis.common.gametype.model.GameType;
import cz.tondracek.inqooltennis.common.price.dto.PriceDto;
import cz.tondracek.inqooltennis.court.dto.CourtDetailDto;
import cz.tondracek.inqooltennis.customer.dto.CustomerDetailDto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationDetailDto(
        UUID id,
        CourtDetailDto courtDetailDto,
        CustomerDetailDto customerDetailDto,
        LocalDateTime startTime,
        LocalDateTime endTime,
        GameType gameType,
        PriceDto price,
        boolean deleted,
        LocalDateTime createdAt
) {
}
