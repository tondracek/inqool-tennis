package cz.tondracek.inqooltennis.reservation.dto;

import cz.tondracek.inqooltennis.common.gametype.model.GameType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateReservationDto(
        @NotNull UUID courtId,
        @NotNull @Future LocalDateTime startTime,
        @NotNull @Future LocalDateTime endTime,
        @NotNull GameType gameType,
        @NotBlank String customerName,
        @NotBlank String customerPhoneNumber
) {
}
