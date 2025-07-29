package cz.tondracek.inqooltennis.customer.dto;

import java.util.UUID;

public record CustomerDetailDto(
        UUID id,
        String phoneNumber,
        String name,
        boolean deleted
) {
}
