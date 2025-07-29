package cz.tondracek.inqooltennis.reservation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationPreviewDto(
        UUID id,
        String courtName,
        String customerName,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
