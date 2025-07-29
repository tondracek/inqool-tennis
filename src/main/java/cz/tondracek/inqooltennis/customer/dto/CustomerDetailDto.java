package cz.tondracek.inqooltennis.customer.dto;

import java.util.UUID;

public record CustomerDetailDto(
        UUID id,
        String name,
        String phoneNumber,
        boolean deleted
) {
}
